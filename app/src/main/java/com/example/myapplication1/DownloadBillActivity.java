package com.example.myapplication1;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;



// -------- Your App Classes --------
import com.example.myapplication1.DatabaseHelper;
import com.example.myapplication1.Bill;
import android.os.Bundle;
import android.os.Environment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;

public class DownloadBillActivity extends AppCompatActivity {

    EditText txtInputBill;
    Button btnDownloadPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_bill);

        txtInputBill = findViewById(R.id.txtInputBill);
        btnDownloadPdf = findViewById(R.id.btnDownloadPdf);

        btnDownloadPdf.setOnClickListener(v -> {
            String billNo = txtInputBill.getText().toString().trim();
            if (billNo.isEmpty()) {
                Toast.makeText(this, "Please enter Bill Number", Toast.LENGTH_SHORT).show();
                return;
            }

            generatePdf(billNo); // ✅ SQLite data se PDF generate
        });
    }

    private void generatePdf(String billNo) {
        Document document = null;

        try {
            // 1️⃣ Create PDF file
            File pdfFile = new File(
                    getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                    "Bill_" + billNo + ".pdf"
            );

            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            // 2️⃣ Get data from DB
            DatabaseHelper db = new DatabaseHelper(this);
           // ShopDetails shop = db.getShopDetails();
            Bill bill = db.getBillByNumber(billNo);

            if (bill == null || bill.items == null || bill.items.isEmpty()) {
                Toast.makeText(this, "Bill data not found", Toast.LENGTH_SHORT).show();
                document.close();
                return;
            }

            // 3️⃣ Fonts
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            // 4️⃣ Shop Details
            Paragraph shopTitle = new Paragraph("M.K. Jwellers", titleFont);
            shopTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(shopTitle);

            Paragraph shopInfo = new Paragraph(
                    "Mobile: 9893180218\nAddress: Bhargava Colony Guna (M.P.)",
                    normalFont
            );
            shopInfo.setAlignment(Element.ALIGN_CENTER);
            document.add(shopInfo);

            document.add(new Paragraph("\n"));

            // 5️⃣ Customer Info
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);

            infoTable.addCell(getCell("Customer: " + bill.customerName, PdfPCell.ALIGN_LEFT));
           // infoTable.addCell(getCell("Date: " + bill.date, PdfPCell.ALIGN_RIGHT));

            infoTable.addCell(getCell("Bill No: " + bill.billNo, PdfPCell.ALIGN_LEFT));
            infoTable.addCell(getCell("", PdfPCell.ALIGN_RIGHT));

            document.add(infoTable);
            document.add(new Paragraph("\n"));

            // 6️⃣ Items Table
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);

            table.addCell("Type");
            table.addCell("Particular");
            table.addCell("Weight");
            table.addCell("Rate");
            table.addCell("Value");
            table.addCell("Making %");
            table.addCell("Amount");

            for (Item item : bill.items) {
                table.addCell(item.type);
                table.addCell(item.particular);
                table.addCell(String.valueOf(item.weight));
                table.addCell(String.valueOf(item.rate));
                table.addCell(String.valueOf(item.value));
                table.addCell(String.valueOf(item.makingPercent));
                table.addCell(String.valueOf(item.amount));
            }

            document.add(table);

            // 7️⃣ Totals
            PdfPTable totals = new PdfPTable(2);
            totals.setWidthPercentage(100);

            totals.addCell(getCell("Total Amount", PdfPCell.ALIGN_LEFT));
            totals.addCell(getCell(String.valueOf(bill.finalAmount), PdfPCell.ALIGN_RIGHT));

            totals.addCell(getCell("Deposited", PdfPCell.ALIGN_LEFT));
            totals.addCell(getCell(String.valueOf(bill.totalDeposit), PdfPCell.ALIGN_RIGHT));

            totals.addCell(getCell("Pending", PdfPCell.ALIGN_LEFT));
            totals.addCell(getCell(String.valueOf(bill.pendingAmount), PdfPCell.ALIGN_RIGHT));

            document.add(totals);

            // 8️⃣ Terms
            document.add(new Paragraph("\n"));
            Paragraph terms = new Paragraph(
                    "* REFUND: 91.6%, 83.3% & 75% HALLMARK (DEDUCT STONE & MEENA).\n" +
                            "* H.M / HUID CHARGES INCLUDED IN MAKING CHARGES.",
                    normalFont
            );
            document.add(terms);

            document.close();

            Toast.makeText(
                    this,
                    "PDF saved in Downloads\n" + pdfFile.getAbsolutePath(),
                    Toast.LENGTH_LONG
            ).show();
            openPdf(pdfFile);

        } catch (Exception e) {
            e.printStackTrace();
            if (document != null) document.close();
            Toast.makeText(this, "PDF Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    private void openPdf(File file) {
        try {
            Uri uri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".provider",
                    file
            );

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(this, "No PDF viewer found", Toast.LENGTH_SHORT).show();
        }
    }

}

