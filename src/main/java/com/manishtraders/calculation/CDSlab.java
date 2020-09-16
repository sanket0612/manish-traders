package com.manishtraders.calculation;

public class CDSlab {
    public static double getDiscountPercent(long daysCount, String s, String p) {
        double disP = 0.0D;


        if (daysCount <= 15L) {
            disP = 0.06D;
        } else if (daysCount > 15L && daysCount <= 25L) {
            disP = 0.05D;
        } else if (daysCount > 25L && daysCount <= 35L) {
            disP = 0.04D;
        } else if (daysCount > 35L && daysCount <= 45L) {
            disP = 0.03D;
        } else if (daysCount > 45L && daysCount <= 60L) {
            disP = 0.02D;
        } else if (daysCount > 60L && daysCount <= 90L) {
            disP = 0.0D;
        }

        if (s.equals("Seeds Sales") || s.equals("Opening Balance") || p.equals("Cash Discount") || s.equals("Late Payment Charges") || s.equals("Sale Gst - Net") || p.equals("Rate Difference - GST") || p.equals("Sale Return Gst")) {

            disP = 0.0D;
            return disP;
        }


        if (daysCount >= 91L) {

            disP = (daysCount - 90L) * -6.7E-4D;


            return disP;
        }


        return disP;
    }
}


