package com.manishtraders.transformer;

import com.manishtraders.calculation.CDSlab;
import com.manishtraders.calculation.CreditNoteExcel;
import com.manishtraders.model.CashDiscount;
import com.manishtraders.model.NewVoucher;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

@Component
public class ExcelExtraction {
    public static String ledgerName;
    public static String dateRange;
    public static String firmName;
    public static double CDsooFar = 0.0D;
    public static double DNsooFar = 0.0D;


    public File ExceltoObject(InputStream file) {
        File output = new File(".");
        try {
            int lastVoucherIndexAdjuster, firstVoucherIndexAdjuster;
            String cn = "Credit Note";
            String dn = "Debit Note";
            String rep = "Receipt";
            String sg = "Sales GST";
            String salRe = "Sales Return Gst";
            String openBal = "Opening Balance";
            String closeBal = "Closing Balance";


            XSSFWorkbook workbook = new XSSFWorkbook(file);


            XSSFSheet sheet = workbook.getSheetAt(0);


            XSSFRow xSSFRow1 = sheet.getRow(5);
            Cell lcell = xSSFRow1.getCell(0);
            ledgerName = lcell.getStringCellValue();
            System.out.println("*********** LEDGER NAME ************" + ledgerName);


            xSSFRow1 = sheet.getRow(9);
            lcell = xSSFRow1.getCell(0);
            dateRange = lcell.getStringCellValue();
            System.out.println("*********** LEDGER NAME ************" + dateRange);


            int z = sheet.getLastRowNum();

            XSSFRow xSSFRow2 = sheet.getRow(z - 1);

            Cell lastRowThirdCell = xSSFRow2.getCell(2);
            String lastRowParticular = lastRowThirdCell.getStringCellValue();

            if (lastRowParticular.equals(closeBal)) {
                lastVoucherIndexAdjuster = 3;
            } else {
                lastVoucherIndexAdjuster = 1;
            }


            ArrayList<NewVoucher> voucherList = new ArrayList<>();
            NewVoucher eob = new NewVoucher();

            XSSFRow xSSFRow3 = sheet.getRow(11);
            Cell firstRowThirdCell = xSSFRow3.getCell(2);
            String firstRowThirdCellValue = firstRowThirdCell.getStringCellValue();

            if (firstRowThirdCellValue.equals(openBal)) {

                Cell firstRowFirstCell = xSSFRow3.getCell(0);
                Cell firstRowSixthCell = xSSFRow3.getCell(5);
                Cell firstRowSeventhCell = xSSFRow3.getCell(6);

                Date mydate = firstRowFirstCell.getDateCellValue();
                SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
                String result = formater.format(mydate);
                System.out.println(result);

                eob.setDate(result);
                eob.setVoucher(firstRowThirdCellValue);
                eob.setParticulars(firstRowThirdCellValue);
                eob.setDebit(Double.valueOf(firstRowSixthCell.getNumericCellValue()));
                eob.setCredit(Double.valueOf(firstRowSeventhCell.getNumericCellValue()));

                voucherList.add(eob);


                firstVoucherIndexAdjuster = 12;
            } else {

                firstVoucherIndexAdjuster = 11;
            }


            for (int i = sheet.getFirstRowNum() + firstVoucherIndexAdjuster; i <= sheet.getLastRowNum() - lastVoucherIndexAdjuster; i++) {

                System.out.println(i);


                int k = sheet.getFirstRowNum() + firstVoucherIndexAdjuster;
                int o = sheet.getLastRowNum() - lastVoucherIndexAdjuster;
                System.out.println(k);
                System.out.println(o);

                NewVoucher e = new NewVoucher();
                XSSFRow xSSFRow = sheet.getRow(i);

                for (int j = xSSFRow.getFirstCellNum(); j <= xSSFRow.getLastCellNum(); j++) {
                    Cell ce = xSSFRow.getCell(j);

                    if (j == 0) {
                        Date mydate = ce.getDateCellValue();
                        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
                        String result = formater.format(mydate);
                        System.out.println(result);
                        e.setDate(result);
                        System.out.println(result);
                    } else if (j == 2) {
                        e.setParticulars(ce.getStringCellValue());
                        System.out.println(ce.getStringCellValue());
                    } else if (j == 3) {
                        e.setVoucher(ce.getStringCellValue());
                        System.out.println(ce.getStringCellValue());

                    } else if (j == 4) {
                        e.setVoucherNo(ce.getStringCellValue());
                        System.out.println(ce.getStringCellValue());

                    } else if (j == 5) {

                        e.setDebit(Double.valueOf(ce.getNumericCellValue()));
                    } else if (j == 6) {

                        e.setCredit(Double.valueOf(ce.getNumericCellValue()));
                    }
                }
                voucherList.add(e);
            }


            Queue<NewVoucher> Sales = new LinkedList<>();
            Queue<NewVoucher> Payments = new LinkedList<>();
            int s = 0, p = 0;


            for (NewVoucher emp : voucherList) {


                String vname = emp.getVoucher();
                String pname = emp.getParticulars();

                if (vname.equals("Journal") || vname.equals(rep) || vname.equals(salRe) || pname.equals("Cash Discount") || pname.equals("Rate Difference - GST")) {

                    p++;
                    Payments.add(emp);

                    if (pname.equals("Cash Discount")) {
                        CDsooFar += emp.getCredit().doubleValue();
                    }

                    continue;
                }

                if (vname.equals(sg) || vname.equals(openBal) || pname.equals("Late Payment Charges") || pname.equals("Bank Exp.")) {

                    s++;
                    Sales.add(emp);
                    if (pname.equals("Late Payment Charges")) {
                        DNsooFar += emp.getDebit().doubleValue();
                    }
                }
            }


            NewVoucher payment = Payments.remove();
            NewVoucher sales = Sales.remove();

            double payAmount = payment.getCredit().doubleValue();
            double salAmount = sales.getDebit().doubleValue();
            double disPercent = 0.0D, disAmount = 0.0D, temp = 0.0D;
            String sParticular = sales.getParticulars();
            String pParticular = payment.getParticulars();

            System.out.println();

            String pDate = payment.getDate();
            String sDate = sales.getDate();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate payDate = LocalDate.parse(pDate, formatter);
            LocalDate salDate = LocalDate.parse(sDate, formatter);


            ArrayList<CashDiscount> creditNoteList = new ArrayList<>();


            while (payAmount != 0.0D) {

                if (payAmount > salAmount) {
                    CashDiscount cashD = new CashDiscount();
                    double creditNoteAmount = salAmount;

                    long daysCount = ChronoUnit.DAYS.between(salDate, payDate);
                    System.out.println("***************Days between: " + daysCount);


                    disPercent = CDSlab.getDiscountPercent(daysCount, sParticular, pParticular);
                    System.out.println("***************CD Percentage between: " + disPercent);

                    disAmount = disPercent * creditNoteAmount;
                    disAmount = Double.parseDouble((new DecimalFormat("##.##")).format(disAmount));


                    temp += disAmount;

                    System.out.println("***************Discount Amount in current case" + disAmount);
                    System.out.println("***************Temp Variable" + temp);

                    System.out.println();

                    cashD.setCreditNo(payment.getVoucherNo());
                    cashD.setCreditAmt(payment.getCredit().doubleValue());
                    cashD.setCreditDate(payment.getDate());
                    cashD.setCreditRemains(payAmount);
                    cashD.setCreditParticulars(payment.getParticulars());

                    cashD.setDebitNo(sales.getVoucherNo());
                    cashD.setDebitAmt(sales.getDebit().doubleValue());
                    cashD.setDebitDate(sales.getDate());
                    cashD.setDebitRemains(salAmount);
                    cashD.setDebitParticulars(sales.getParticulars());

                    cashD.setDayCount(daysCount);
                    cashD.setCdPercent(disPercent);
                    cashD.setCdAmount(disAmount);
                    cashD.setCdApplicableAmount(creditNoteAmount);
                    creditNoteList.add(cashD);

                    salAmount = 0.0D;
                    payAmount -= creditNoteAmount;


                    disAmount = 0.0D;

                    if (!Sales.isEmpty()) {

                        sales = Sales.remove();
                        sDate = sales.getDate();
                        salAmount = sales.getDebit().doubleValue();
                        sParticular = sales.getParticulars();

                        salDate = LocalDate.parse(sDate, formatter);
                    }
                }

                if (payAmount < salAmount) {

                    CashDiscount cashD = new CashDiscount();

                    double creditNoteAmount = payAmount;

                    System.out.println(" Applicable amount for discount  " + creditNoteAmount);
                    long daysCount = ChronoUnit.DAYS.between(salDate, payDate);
                    System.out.println("***************Days between: " + daysCount);

                    disPercent = CDSlab.getDiscountPercent(daysCount, sParticular, pParticular);
                    System.out.println("***************CD Percentage between: " + disPercent);

                    disAmount = disPercent * creditNoteAmount;
                    disAmount = Double.parseDouble((new DecimalFormat("##.##")).format(disAmount));

                    temp += disAmount;

                    System.out.println("***************Discount Amount in current case" + disAmount);

                    System.out.println("***************Temp Variable" + temp);
                    System.out.println();

                    cashD.setCreditNo(payment.getVoucherNo());
                    cashD.setCreditAmt(payment.getCredit().doubleValue());
                    cashD.setCreditDate(payment.getDate());
                    cashD.setCreditRemains(payAmount);
                    cashD.setCreditParticulars(payment.getParticulars());

                    cashD.setDebitNo(sales.getVoucherNo());
                    cashD.setDebitAmt(sales.getDebit().doubleValue());
                    cashD.setDebitDate(sales.getDate());
                    cashD.setDebitRemains(salAmount);
                    cashD.setDebitParticulars(sales.getParticulars());

                    cashD.setDayCount(daysCount);
                    cashD.setCdPercent(disPercent);
                    cashD.setCdAmount(disAmount);
                    cashD.setCdApplicableAmount(creditNoteAmount);
                    creditNoteList.add(cashD);


                    disAmount = 0.0D;
                    payAmount = 0.0D;
                    salAmount -= creditNoteAmount;

                    if (!Payments.isEmpty()) {

                        payment = Payments.remove();
                        payAmount = payment.getCredit().doubleValue();
                        pDate = payment.getDate();

                        payDate = LocalDate.parse(pDate, formatter);
                        pParticular = payment.getParticulars();
                    }
                }

                if (payAmount == salAmount) {

                    CashDiscount cashD = new CashDiscount();
                    double creditNoteAmount = payAmount;
                    System.out.println(" Applicable amount for discount  " + creditNoteAmount);


                    long daysCount = ChronoUnit.DAYS.between(salDate, payDate);

                    System.out.println("*************** Days between: " + daysCount);
                    disPercent = CDSlab.getDiscountPercent(daysCount, sParticular, pParticular);
                    System.out.println("*************** CD Percentage between: " + disPercent);

                    disAmount = disPercent * creditNoteAmount;
                    disAmount = Double.parseDouble((new DecimalFormat("##.##")).format(disAmount));

                    temp += disAmount;

                    System.out.println("***************Temp Variable" + temp);

                    cashD.setCreditNo(payment.getVoucherNo());
                    cashD.setCreditAmt(payment.getCredit().doubleValue());
                    cashD.setCreditDate(payment.getDate());
                    cashD.setCreditRemains(payAmount);
                    cashD.setCreditParticulars(payment.getParticulars());

                    cashD.setDebitNo(sales.getVoucherNo());
                    cashD.setDebitAmt(sales.getDebit().doubleValue());
                    cashD.setDebitDate(sales.getDate());
                    cashD.setDebitRemains(salAmount);
                    cashD.setDebitParticulars(sales.getParticulars());

                    cashD.setDayCount(daysCount);
                    cashD.setCdPercent(disPercent);
                    cashD.setCdAmount(disAmount);
                    cashD.setCdApplicableAmount(creditNoteAmount);
                    creditNoteList.add(cashD);


                    disAmount = 0.0D;
                    payAmount = 0.0D;
                    salAmount = 0.0D;
                    if (!Sales.isEmpty()) {

                        sales = Sales.remove();
                        salAmount = sales.getDebit().doubleValue();
                        sDate = sales.getDate();
                        salDate = LocalDate.parse(sDate, formatter);
                        sParticular = sales.getParticulars();
                    }

                    if (!Payments.isEmpty()) {

                        payment = Payments.remove();
                        payAmount = payment.getCredit().doubleValue();
                        pDate = payment.getDate();
                        payDate = LocalDate.parse(pDate, formatter);
                        pParticular = payment.getParticulars();
                    }
                }
            }

            String tempo = "";
            LocalDate today = LocalDate.now();

            while (salAmount != 0.0D) {


                long daysCount = ChronoUnit.DAYS.between(salDate, today);
                CashDiscount cashD = new CashDiscount();
                disPercent = CDSlab.getDiscountPercent(daysCount, sParticular, tempo);

                if (daysCount < 91L) {
                    disPercent = 0.0D;
                }
                double creditNoteAmount = salAmount;
                disAmount = creditNoteAmount * disPercent;
                disAmount = Double.parseDouble((new DecimalFormat("##.##")).format(disAmount));

                cashD.setCreditNo("Not");

                cashD.setCreditDate("Payment");

                cashD.setCreditParticulars("Received");

                cashD.setDebitNo(sales.getVoucherNo());
                cashD.setDebitAmt(sales.getDebit().doubleValue());
                cashD.setDebitDate(sales.getDate());
                cashD.setDebitRemains(salAmount);
                cashD.setDebitParticulars(sales.getParticulars());

                cashD.setDayCount(daysCount);
                cashD.setCdPercent(disPercent);
                cashD.setCdAmount(disAmount);
                cashD.setCdApplicableAmount(creditNoteAmount);
                creditNoteList.add(cashD);

                salAmount = 0.0D;

                if (!Sales.isEmpty()) {

                    sales = Sales.remove();
                    salAmount = sales.getDebit().doubleValue();
                    sDate = sales.getDate();
                    salDate = LocalDate.parse(sDate, formatter);
                    sParticular = sales.getParticulars();
                }
            }

            CreditNoteExcel cne = new CreditNoteExcel();
            output = cne.ListToExcel(creditNoteList);

            file.close();
            return output;
        } catch (Exception e) {
            int checker = 0;
        }
        return output;
    }
}


