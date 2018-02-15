package com.money.expencetracker.helpers;

import android.text.TextUtils;
import android.util.Log;

import com.money.expencetracker.data.entities.Account;
import com.money.expencetracker.data.entities.Transaction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by abhishek on 22/12/17.
 */

public class SmsReadHelper {

    private String regExpForCardNo = "(Account|a/c no(.)?|Card\\s(no)?(.)?\\s?(ending)?|A/c)\\s(\\d+)?(x*X*)?\\d{4}(\\d+)?\\s";
    private String regExpForTransactionAmount = "(credited (with|by|of)|for|by|Tranx of|debited (with|by|of)?|(amount|payment|Transfer) of|Rs.|Inr|through ATM by)(\\s?(INR|Rs(.)?)?)(\\s)?(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d+)?)";
    private String regExpForUPITransactionAmount = "(\\s)?(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d+)?) is debited from";
    private String regExpForAvailableBalance = "(balance(:)?(\\sis)?|Avbl Cr lmt:|Avl bal\\s?:|Bal|balance on your card is|total Bal :)\\s?(INR|Rs(.)?)\\s?(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d+)?)";
    private String regExpForDepositMoney = "(credited|[aA]dded|Received|deposited)";
    private String regExpForSpentMoney = "(withdrawn|Thank you for using Debit Card|spent on|made at|Purchase for|using Debit Card|debited|\\sDr\\s|payment of)";
    private String regExpForMerchantName = "\\s(at|Info:).{2,30}";
    private String regExpForAccountName = "(AXIS|AIRBNK|IPAYTM|SODEXO|ICICIB|HDFCBK|AlBANK|FROMSC|BWCBSSBI|AXISBK|CITIBK)";
    private String regExpForCreditCard = "(credit card\\s(\\d+)?(x*X*)?\\d{4})";

    public SmsReadHelper() {
    }

    public void buildAccountObject(Account account, String smsBody, Double smsTimeStamp) {
        if (account == null) return;
        Pattern availableBalancePattern = Pattern.compile(regExpForAvailableBalance, Pattern.CASE_INSENSITIVE);
        Matcher availableBalanceMatcher = availableBalancePattern.matcher(smsBody);
        String balance = null;
        if (availableBalanceMatcher.find()) {
            String balanceStr[] = availableBalanceMatcher.group().trim().split(" ");
            balance = balanceStr[balanceStr.length - 1];
        }
        if (account.getTimestamp() != null) {
            if (account.getTimestamp() < smsTimeStamp && !TextUtils.isEmpty(balance)) {
                account.setAccountBalance(balance);
                account.setTimestamp(smsTimeStamp);
            }
        } else if (!TextUtils.isEmpty(balance)){
            account.setAccountBalance(balance);
            account.setTimestamp(smsTimeStamp);
        }
    }

    public void buildTransactionObject(Transaction transaction, String smsBody) {
        if (transaction == null) return;

        Pattern transactionAmountPattern = Pattern.compile(regExpForTransactionAmount, Pattern.CASE_INSENSITIVE);
        Matcher transactionAmountMatcher = transactionAmountPattern.matcher(smsBody);

        Pattern upiTransactionAmountPattern = Pattern.compile(regExpForUPITransactionAmount, Pattern.CASE_INSENSITIVE);
        Matcher upiTransactionAmountMatcher = upiTransactionAmountPattern.matcher(smsBody);

        Pattern depositMoneyPattern = Pattern.compile(regExpForDepositMoney, Pattern.CASE_INSENSITIVE);
        Matcher depositMoneyMatcher = depositMoneyPattern.matcher(smsBody);

        Pattern spentMoneyPattern = Pattern.compile(regExpForSpentMoney, Pattern.CASE_INSENSITIVE);
        Matcher spentMoneyMatcher = spentMoneyPattern.matcher(smsBody);

        Pattern merchantNamePattern = Pattern.compile(regExpForMerchantName, Pattern.CASE_INSENSITIVE);
        Matcher merchantNameMatcher = merchantNamePattern.matcher(smsBody);


        // checking for transaction amount if any
        if (transactionAmountMatcher.find()) {
            if (!TextUtils.isEmpty(transactionAmountMatcher.group())) {
                String [] transactionAmountArr = transactionAmountMatcher.group().trim().split(" ");
                transaction.setAmount(transactionAmountArr[transactionAmountArr.length-1]);
            }
        } else if (upiTransactionAmountMatcher.find()){
           if (!TextUtils.isEmpty(upiTransactionAmountMatcher.group())) {
               String [] transactionAmountArr = upiTransactionAmountMatcher.group().trim().split(" ");
               transaction.setAmount(transactionAmountArr[0]);
           }
        }

        // checking for incoming money or not
        if (depositMoneyMatcher.find()) {
            if (depositMoneyMatcher.group().length() != 0) {
                transaction.setTransactionType("incoming");
            }
        }

        // checking for outgoing money or not
        if (spentMoneyMatcher.find()) {
            if (spentMoneyMatcher.group().length() != 0) {
                transaction.setTransactionType("outgoing");
            }
        }

        // checking for merchant name
        if (merchantNameMatcher.find()) {
            if (merchantNameMatcher.group().length() != 0) {
                StringBuilder merchantName = new StringBuilder();
                String[] merchantArr = merchantNameMatcher.group().trim().split(" ");
                if (merchantArr.length > 1){
                    int count = 2;
                    for ( int i = 1 ; i < merchantArr.length ; i++ ) {
                        if (count > 0){
                            merchantName.append(merchantArr[i]).append(" ");
                            count--;
                        }
                    }
                }
                transaction.setMerchantName(merchantName.toString());
            }
        }
    }

    public String getAccountCardNo(String smsBody) {
        Pattern cardNoPattern = Pattern.compile(regExpForCardNo, Pattern.CASE_INSENSITIVE);
        Matcher cardNoMatcher = cardNoPattern.matcher(smsBody);
        String cardNo = null;
        // checking for card no in sms body in any
        if (cardNoMatcher.find()) {
            if (cardNoMatcher.group().length() != 0) {
                cardNo = cardNoMatcher.group().trim();
            }
        }
        return cardNo;
    }

    public String getAccountName(String senderName, String smsBody) {
        StringBuilder accountName = new StringBuilder();
        Pattern accountNamePattern = Pattern.compile(regExpForAccountName, Pattern.CASE_INSENSITIVE);
        Matcher accountNameMatcher = accountNamePattern.matcher(senderName);
        if (accountNameMatcher.find()) {
            String matchedName = accountNameMatcher.group().trim().toUpperCase();
            String acName = "";
            Pattern creditCardPattern = Pattern.compile(regExpForCreditCard, Pattern.CASE_INSENSITIVE);
            Matcher creditCardMatcher = creditCardPattern.matcher(smsBody);
            //(AXIS|AIRBNK|IPAYTM|SODEXO|ICICIB|HDFCBK|AlBANK|FROMSC|BWCBSSBI|AXISBK)
            switch (matchedName) {
                case "IPAYTM":
                    acName = "Paytm";
                    break;
                case "SODEXO":
                    acName = "Sodexo";
                    break;
                case "AIRBNK":
                    acName = "Airtel Bank";
                    break;
                case "HDFCBK":
                    acName = "HDFC";
                    break;
                case "ICICIB":
                    acName = "ICICI";
                    break;
                case "AXISBK":
                    acName = "Axis bank";
                    break;
                case "BWCBSSBI":
                    acName = "SBI bank";
                    break;
                case "FROMSC":
                    acName = "Standard Charter";
                    break;
                case "ALBANK":
                    acName = "Allahabad bank ";
                    break;
                case "CITI":
                    acName = "Citi bank ";
                    break;
            }
            accountName.append(acName);
            if (creditCardMatcher.find()) {
                accountName.append(" ").append("Credit Card");
            }
        }
        return accountName.toString();
    }

    public boolean isOtpMessage(String smsBody) {
        if (smsBody.toLowerCase().contains("otp")) {
            return true;
        }
        return false;
    }

}
