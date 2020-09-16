package com.manishtraders.calculation;

import com.manishtraders.model.CashDiscount;
import com.manishtraders.transformer.ExcelExtraction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class CreditNoteExcel {
    static int CDExcelNo = 1;
    double CDTotal = 0.0D;
    double DNTotal = 0.0D;


    public File ListToExcel(ArrayList<CashDiscount> CRDNList) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet s = workbook.createSheet();


        XSSFCellStyle style1 = workbook.createCellStyle();
        XSSFFont font1 = workbook.createFont();
        font1.setBold(true);
        font1.setFontHeightInPoints((short) 14);
        style1.setFont((Font) font1);
        style1.setAlignment(HorizontalAlignment.CENTER);


        XSSFCellStyle style2 = workbook.createCellStyle();
        XSSFFont font2 = workbook.createFont();
        font2.setBold(true);
        font2.setFontHeightInPoints((short) 12);
        style2.setFont((Font) font2);
        style2.setAlignment(HorizontalAlignment.CENTER);


        XSSFCellStyle style3 = workbook.createCellStyle();
        XSSFFont font3 = workbook.createFont();
        font3.setBold(false);
        font3.setFontHeightInPoints((short) 10);
        style3.setFont((Font) font3);
        style3.setAlignment(HorizontalAlignment.CENTER);


        XSSFCellStyle style4 = workbook.createCellStyle();
        XSSFFont font4 = workbook.createFont();
        font4.setBold(true);
        font4.setFontHeightInPoints((short) 10);
        style4.setFont((Font) font4);
        style4.setAlignment(HorizontalAlignment.LEFT);
        style4.setBorderBottom(BorderStyle.THICK);
        style4.setBorderTop(BorderStyle.THIN);
        style4.setBorderLeft(BorderStyle.THIN);
        style4.setBorderRight(BorderStyle.THIN);
        style4.setWrapText(true);


        XSSFCellStyle style5 = workbook.createCellStyle();
        XSSFFont font5 = workbook.createFont();
        font5.setBold(false);
        font5.setFontHeightInPoints((short) 10);
        style5.setFont((Font) font5);
        style5.setBorderBottom(BorderStyle.THIN);
        style5.setBorderLeft(BorderStyle.THIN);
        style5.setBorderRight(BorderStyle.THIN);


        XSSFCellStyle style6 = workbook.createCellStyle();
        XSSFFont font6 = workbook.createFont();
        font6.setBold(false);
        font6.setFontHeightInPoints((short) 10);
        style6.setFont((Font) font6);
        style6.setAlignment(HorizontalAlignment.LEFT);


        XSSFCellStyle style7 = workbook.createCellStyle();
        XSSFFont font7 = workbook.createFont();
        font7.setBold(true);
        font7.setFontHeightInPoints((short) 10);
        style7.setFont((Font) font7);

        s.addMergedRegion(new CellRangeAddress(1, 1, 0, 13));
        XSSFRow xSSFRow1 = s.createRow(1);
        Cell cShopName = xSSFRow1.createCell(0);
        cShopName.setCellValue("Manish Traders Ahirkheda 20-21");
        cShopName.setCellStyle((CellStyle) style1);

        s.addMergedRegion(new CellRangeAddress(3, 3, 0, 13));
        XSSFRow xSSFRow2 = s.createRow(3);
        Cell cledgerName = xSSFRow2.createCell(0);
        cledgerName.setCellValue(ExcelExtraction.ledgerName);
        cledgerName.setCellStyle((CellStyle) style2);

        s.addMergedRegion(new CellRangeAddress(4, 4, 0, 13));
        XSSFRow xSSFRow3 = s.createRow(4);
        Cell creportType = xSSFRow3.createCell(0);
        creportType.setCellValue("DETAILED CASH DISCOUNT / INTEREST REPORT");
        creportType.setCellStyle((CellStyle) style3);

        s.addMergedRegion(new CellRangeAddress(5, 5, 0, 13));
        XSSFRow xSSFRow4 = s.createRow(5);
        Cell cdateRange = xSSFRow4.createCell(0);
        cdateRange.setCellValue("(" + ExcelExtraction.dateRange + ")");
        cdateRange.setCellStyle((CellStyle) style3);

        s.setColumnWidth(0, 3000);


        String[] tableHeader = {"Receipt Date", "Receipt No", "Receipt Parti.", "Receipt Amount", "Receipt Remains",
                "Invoice Date", "Invoice No", "Invoice Parti.", "Invoice Amount", "Invoice Remains",
                "Payment Received(in Days)", "CD Percent(%)", "CD Amount"};

        XSSFRow xSSFRow5 = s.createRow(8);

        Cell cell80 = xSSFRow5.createCell(0);
        cell80.setCellValue("Receipt Date");
        cell80.setCellStyle((CellStyle) style4);

        Cell cell81 = xSSFRow5.createCell(1);
        cell81.setCellValue("Receipt No");
        cell81.setCellStyle((CellStyle) style4);

        Cell cell82 = xSSFRow5.createCell(2);
        cell82.setCellValue("Receipt Particulars");
        cell82.setCellStyle((CellStyle) style4);

        Cell cell83 = xSSFRow5.createCell(3);
        cell83.setCellValue("Receipt Amount");
        cell83.setCellStyle((CellStyle) style4);

        Cell cell84 = xSSFRow5.createCell(4);
        cell84.setCellValue("Receipt Remains");
        cell84.setCellStyle((CellStyle) style4);

        Cell cell85 = xSSFRow5.createCell(5);
        cell85.setCellValue("Invoice Date");
        cell85.setCellStyle((CellStyle) style4);

        Cell cell86 = xSSFRow5.createCell(6);
        cell86.setCellValue("Invoice No");
        cell86.setCellStyle((CellStyle) style4);

        Cell cell87 = xSSFRow5.createCell(7);
        cell87.setCellValue("Invoice Particulars");
        cell87.setCellStyle((CellStyle) style4);

        Cell cell88 = xSSFRow5.createCell(8);
        cell88.setCellValue("Invoice Amount");
        cell88.setCellStyle((CellStyle) style4);

        Cell cell89 = xSSFRow5.createCell(9);
        cell89.setCellValue("Invoice Remains");
        cell89.setCellStyle((CellStyle) style4);

        Cell cell810 = xSSFRow5.createCell(10);
        cell810.setCellValue("No Of Days Payment Received");
        cell810.setCellStyle((CellStyle) style4);

        Cell cell811 = xSSFRow5.createCell(11);
        cell811.setCellValue("Applicable CD Amount");
        cell811.setCellStyle((CellStyle) style4);

        Cell cell812 = xSSFRow5.createCell(12);
        cell812.setCellValue("CD Percent (%)");
        cell812.setCellStyle((CellStyle) style4);

        Cell cell813 = xSSFRow5.createCell(13);
        cell813.setCellValue("CD Amount");
        cell813.setCellStyle((CellStyle) style4);

        s.setRepeatingRows(new CellRangeAddress(8, 8, 0, 13));
        s.setAutobreaks(true);


        XSSFPrintSetup ps = s.getPrintSetup();
        ps.setLandscape(true);
        ps.setOrientation(PrintOrientation.LANDSCAPE);

        int i = 9;
        for (CashDiscount cds : CRDNList) {

            xSSFRow5 = s.createRow(i);

            Cell c0 = xSSFRow5.createCell(0);
            c0.setCellValue(cds.getCreditDate());
            c0.setCellStyle((CellStyle) style5);

            Cell c1 = xSSFRow5.createCell(1);
            c1.setCellValue(cds.getCreditNo());
            c1.setCellStyle((CellStyle) style5);

            Cell c2 = xSSFRow5.createCell(2);
            c2.setCellValue(cds.getCreditParticulars());
            c2.setCellStyle((CellStyle) style5);

            Cell c3 = xSSFRow5.createCell(3);
            c3.setCellValue(cds.getCreditAmt());
            c3.setCellStyle((CellStyle) style5);

            Cell c4 = xSSFRow5.createCell(4);
            c4.setCellValue(cds.getCreditRemains());
            c4.setCellStyle((CellStyle) style5);

            Cell c5 = xSSFRow5.createCell(5);
            c5.setCellValue(cds.getDebitDate());
            c5.setCellStyle((CellStyle) style5);

            Cell c6 = xSSFRow5.createCell(6);
            c6.setCellValue(cds.getDebitNo());
            c6.setCellStyle((CellStyle) style5);

            Cell c7 = xSSFRow5.createCell(7);
            c7.setCellValue(cds.getDebitParticulars());
            c7.setCellStyle((CellStyle) style5);

            Cell c8 = xSSFRow5.createCell(8);
            c8.setCellValue(cds.getDebitAmt());
            c8.setCellStyle((CellStyle) style5);

            Cell c9 = xSSFRow5.createCell(9);
            c9.setCellValue(cds.getDebitRemains());
            c9.setCellStyle((CellStyle) style5);

            Cell c10 = xSSFRow5.createCell(10);
            c10.setCellValue(cds.getDayCount());
            c10.setCellStyle((CellStyle) style5);

            Cell c11 = xSSFRow5.createCell(11);
            c11.setCellValue(cds.getCdApplicableAmount());
            c11.setCellStyle((CellStyle) style5);

            Cell c12 = xSSFRow5.createCell(12);
            c12.setCellValue(100.0D * cds.getCdPercent());
            c12.setCellStyle((CellStyle) style5);

            Cell c13 = xSSFRow5.createCell(13);
            c13.setCellValue(cds.getCdAmount());
            c13.setCellStyle((CellStyle) style5);

            if (cds.getCdAmount() >= 0.0D) {

                this.CDTotal += cds.getCdAmount();

            } else {

                this.DNTotal += cds.getCdAmount();
            }

            i++;
        }
        i += 4;
        String TotalCreditNoteLabel = "Total CD Calculated(Till Date)";
        String CNGivenSooFarLabel = "CD Credited to Account Soo far";
        String CNRemainsLabel = "CD Remaining to give";

        String TotalLFLabel = "Total Late Fee Calculated(Till Date)";
        String LFSoofarLabel = "Late Fee Debited to Account Soo far";
        String LFRemainsLabel = "Late Fee Remaining to give";


        XSSFRow xSSFRow6 = s.createRow(i - 2);
        s.addMergedRegion(new CellRangeAddress(i - 2, i - 2, 0, 13));
        Cell ccd0 = xSSFRow6.createCell(0);
        ccd0.setCellValue("Report Summary");
        ccd0.setCellStyle((CellStyle) style2);


        XSSFRow xSSFRow7 = s.createRow(i);
        s.addMergedRegion(new CellRangeAddress(i, i, 0, 4));
        Cell ccd01 = xSSFRow7.createCell(0);
        ccd01.setCellValue("Cash Discount Summary");
        ccd01.setCellStyle((CellStyle) style7);

        XSSFRow xSSFRow8 = s.createRow(i + 1);
        s.addMergedRegion(new CellRangeAddress(i + 1, i + 1, 0, 2));
        Cell ccd1 = xSSFRow8.createCell(0);
        ccd1.setCellValue(TotalCreditNoteLabel);
        ccd1.setCellStyle((CellStyle) style6);

        s.addMergedRegion(new CellRangeAddress(i + 1, i + 1, 3, 4));
        Cell ccd11 = xSSFRow8.createCell(3);
        ccd11.setCellValue(this.CDTotal);
        ccd11.setCellStyle((CellStyle) style7);


        XSSFRow xSSFRow9 = s.createRow(i + 2);
        s.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 2));
        Cell ccd2 = xSSFRow9.createCell(0);
        ccd2.setCellValue(CNGivenSooFarLabel);
        ccd2.setCellStyle((CellStyle) style6);

        s.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 3, 4));
        Cell ccd21 = xSSFRow9.createCell(3);
        ccd21.setCellValue(ExcelExtraction.CDsooFar);
        ccd21.setCellStyle((CellStyle) style7);


        XSSFRow xSSFRow10 = s.createRow(i + 3);
        s.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 2));
        Cell ccd3 = xSSFRow10.createCell(0);
        ccd3.setCellValue(CNRemainsLabel);
        ccd3.setCellStyle((CellStyle) style6);

        s.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 3, 4));
        Cell ccd31 = xSSFRow10.createCell(3);
        double CDRemains = this.CDTotal - ExcelExtraction.CDsooFar;
        ccd31.setCellValue(CDRemains);
        ccd31.setCellStyle((CellStyle) style7);


        s.addMergedRegion(new CellRangeAddress(i, i, 8, 12));
        Cell ccd02 = xSSFRow7.createCell(8);
        ccd02.setCellValue("Late Fee Interest Summary");
        ccd02.setCellStyle((CellStyle) style7);


        s.addMergedRegion(new CellRangeAddress(i + 1, i + 1, 8, 10));
        Cell ci1 = xSSFRow8.createCell(8);
        ci1.setCellValue(TotalLFLabel);
        ci1.setCellStyle((CellStyle) style6);

        s.addMergedRegion(new CellRangeAddress(i + 1, i + 1, 11, 12));
        Cell ci11 = xSSFRow8.createCell(11);
        ci11.setCellValue(this.DNTotal);
        ci11.setCellStyle((CellStyle) style7);


        s.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 8, 10));
        Cell ci2 = xSSFRow9.createCell(8);
        ci2.setCellValue(LFSoofarLabel);
        ci2.setCellStyle((CellStyle) style6);

        s.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 11, 12));
        Cell ci21 = xSSFRow9.createCell(11);
        ci21.setCellValue(ExcelExtraction.DNsooFar);
        ci21.setCellStyle((CellStyle) style7);


        double DNRemains = this.DNTotal - ExcelExtraction.DNsooFar;


        s.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 8, 10));
        Cell ci3 = xSSFRow10.createCell(8);
        ci3.setCellValue(LFRemainsLabel);
        ci3.setCellStyle((CellStyle) style6);

        s.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 11, 12));
        Cell ci31 = xSSFRow10.createCell(11);
        ci31.setCellValue(DNRemains);
        ci31.setCellStyle((CellStyle) style7);

        s.addMergedRegion(new CellRangeAddress(i + 5, i + 5, 0, 13));
        XSSFRow xSSFRow11 = s.createRow(i + 5);
        Cell FinalMerged = xSSFRow11.createCell(0);
        FinalMerged.setCellStyle((CellStyle) style5);

        String absPath = getFileName();
        File file = new File(absPath);
        try {
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);

            out.close();
            CDExcelNo++;

            int checker = 1;

        } catch (Exception e) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write("Unable to create File".getBytes());
            e.printStackTrace();
            int checker = 0;
        }
        return file;
    }

    private String getFileName() {
            return "output.xlsx";
    }

    private String getFilePath() {
        File file = new File(".");
        String path = file.getAbsolutePath();
        return path.substring(0,path.length()-1);
    }
}


