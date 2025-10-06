package com.jobportal.utility;

public class Data {
    public static String getMessageBody(String otp,String name){
        return  "<!DOCTYPE html>"
                + "<html lang='en'>"
                + "<head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                + "<title>OTP Verification</title></head>"
                + "<body style='font-family: Arial, sans-serif; background-color: #f4f6f8; padding: 20px; color: #333;'>"
                + "<div style='max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; padding: 30px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);'>"

                // Green header background
                + "<div style='text-align: center; margin-bottom: 20px; background-color: #28a745; padding: 15px; border-radius: 6px;'>"
                + "<h2 style='margin: 0; color: white;'>Email Verification</h2>"
                + "</div>"

                + "<p>Hello "+name+",</p>"
                + "<p>Use the following One-Time Password (OTP) to complete your verification process. This OTP is valid for 10 minutes.</p>"

                // OTP block with green text
                + "<div style='font-size: 28px; font-weight: bold; letter-spacing: 4px; color: #28a745; background-color: #f1f1f1; padding: 12px; border-radius: 5px; display: inline-block; margin: 20px 0;'>"
                + otp
                + "</div>"

                + "<p>If you did not request this code, you can safely ignore this email.</p>"
                + "<div style='text-align: center; font-size: 13px; color: #999; margin-top: 30px;'>&copy; 2025 JobWaves. All rights reserved.</div>"
                + "</div>"
                + "</body>"
                + "</html>";

    };
}
