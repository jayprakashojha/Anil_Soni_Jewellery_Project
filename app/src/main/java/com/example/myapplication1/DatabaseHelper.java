package com.example.myapplication1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "jewellery11.db";
    private static final int DB_VERSION = 2;

    public static final String TABLE_CUSTOMER = "customers";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_MOBILE = "mobile";
    public static final String COL_DEPOSIT = "deposit";
    public static final String COL_DATE = "date";
    public static final String COL_ADDRESS = "address";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_TYPE = "type";
    public static final String COL_WEIGHT = "weight";
    public static final String COL_RATE = "rate";
    public static final String COL_VALUE = "value";
    public static final String COL_MAKING = "making";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_GRAND_TOTAL = "grand_total";
    public static final String COL_PENDING_AMOUNT = "pendingAmount";

    public static final String COL_BILL_NO = "billNo";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_CUSTOMER + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_MOBILE + " TEXT, " +
                COL_DATE + " TEXT, " +
                COL_ADDRESS + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_TYPE + " TEXT, " +
                COL_WEIGHT + " REAL, " +
                COL_RATE + " REAL, " +
                COL_VALUE + " REAL, " +
                COL_MAKING + " REAL, " +

                COL_GRAND_TOTAL + " REAL, " +
                COL_DEPOSIT + " REAL, " +
                COL_PENDING_AMOUNT + " REAL," +
               // COL_AMOUNT + " REAL, " +
                COL_BILL_NO + " INTEGER)";


        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        onCreate(db);
    }


    public boolean insertCustomer(String name, String mobile, String date, String address, String description, String type,
                                  double weight,
                                  double rate,
                                  double value,
                                  double making,
                                  double grandTotal, double deposit, double pendingAmount, String billNo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_MOBILE, mobile);
        cv.put(COL_DATE, date);
        cv.put(COL_ADDRESS, address);
        cv.put(COL_DESCRIPTION, description);
        cv.put(COL_TYPE, type);
        cv.put(COL_WEIGHT, weight);
        cv.put(COL_RATE, rate);
        cv.put(COL_VALUE, value);
        cv.put(COL_MAKING, making);

        cv.put(COL_GRAND_TOTAL,grandTotal);
        cv.put(COL_DEPOSIT, deposit);
        cv.put(COL_PENDING_AMOUNT, pendingAmount);
        //cv.put(COL_AMOUNT, amount);
        cv.put(COL_BILL_NO, billNo);

        long result = db.insert(TABLE_CUSTOMER, null, cv);
        return result != -1;
    }

    public Cursor getAllCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CUSTOMER + " ORDER BY id ASC", null);
    }

    public List<Bean> getAllBills() {

        List<Bean> billList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " +
                COL_ID + ", " +
                COL_BILL_NO + ", " +
                COL_DESCRIPTION + "," +
                COL_NAME + ", " +
                COL_MOBILE + ", " +
                COL_AMOUNT + ", " +
                COL_DEPOSIT + ", " +
                COL_PENDING_AMOUNT +
                " FROM " + TABLE_CUSTOMER +
                " ORDER BY " + COL_ID + " asc";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Bean bill = new Bean(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                       cursor.getDouble(5),
                       cursor.getDouble(6),
                       cursor.getDouble(7)
                     //   cursor.getDouble(8)*/



                );
                billList.add(bill);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return billList;
    }

    public Bill getBillById(int billId) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + COL_ID + " = ?",
                new String[]{String.valueOf(billId)}
        );

        Bill bill = null;

        if (c.moveToFirst()) {
            bill = new Bill();
            bill.id = c.getInt(c.getColumnIndexOrThrow(COL_ID));
            bill.billNo = c.getInt(c.getColumnIndexOrThrow(COL_BILL_NO));
            bill.customerName = c.getString(c.getColumnIndexOrThrow(COL_NAME));
            bill.finalAmount = c.getDouble(c.getColumnIndexOrThrow(COL_AMOUNT));
            bill.totalDeposit = c.getDouble(c.getColumnIndexOrThrow(COL_DEPOSIT));

            // 🔥 Important
            bill.pendingAmount = bill.finalAmount - bill.totalDeposit;
        }

        c.close();
        return bill;
    }

    public Bill getBillByNumber(String billNo) {

        SQLiteDatabase db = this.getReadableDatabase();
        Bill bill = null;
        List<Item> itemList = new ArrayList<>();

        Cursor c = db.rawQuery(
                "SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + COL_BILL_NO + " = ?",
                new String[]{ billNo }
        );

        if (c != null && c.moveToFirst()) {

            bill = new Bill();
            bill.billNo = Integer.parseInt(billNo);
            bill.customerName = c.getString(c.getColumnIndexOrThrow(COL_NAME));
            bill.totalDeposit = c.getDouble(c.getColumnIndexOrThrow(COL_DEPOSIT));
            bill.pendingAmount = c.getDouble(c.getColumnIndexOrThrow(COL_PENDING_AMOUNT));
            bill.finalAmount = 0;

            do {
                Item item = new Item();
                item.type = c.getString(c.getColumnIndexOrThrow(COL_TYPE));
                item.particular = c.getString(c.getColumnIndexOrThrow(COL_DESCRIPTION));
                item.weight = c.getDouble(c.getColumnIndexOrThrow(COL_WEIGHT));
                item.rate = c.getDouble(c.getColumnIndexOrThrow(COL_RATE));
                item.value = c.getDouble(c.getColumnIndexOrThrow(COL_VALUE));
                item.makingPercent = c.getDouble(c.getColumnIndexOrThrow(COL_MAKING));
                item.amount = c.getDouble(c.getColumnIndexOrThrow(COL_AMOUNT));

                bill.finalAmount += item.amount;
                itemList.add(item);

            } while (c.moveToNext());

            bill.items = itemList;
        }

        if (c != null) c.close();
        db.close();

        return bill;
    }

}
