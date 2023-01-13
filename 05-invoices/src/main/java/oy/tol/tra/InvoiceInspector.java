package oy.tol.tra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InvoiceInspector {

   /** List of invoices sent to customer. */
   Invoice[] invoices = null;
   /** List of payments received from customers. */
   Payment[] payments = null;
   /**
    * Based on invoices and payments, a list of new invoices to be sent to
    * customers. DO NOT use Java containers in your implementation, it is used ONLY here
    * to store the invoices to collect. Use plain Java arrays {@code Invoice[]} and {@code Payment[]} 
    */
   List<Invoice> toCollect = new ArrayList<>();

   /**
    * Reads the invoices and payments to memory from csv text files.
    * 
    * @param invoicesFile The file containing the invoice data.
    * @param paymentsFile The file containing the payments data.
    * @throws IOException If file thing goes wrong, there will be exception.
    */
   public void readInvoicesAndPayments(String invoicesFile, String paymentsFile) throws IOException {
      BufferedReader invoiceReader = new BufferedReader(new InputStreamReader(new FileInputStream(invoicesFile), "UTF-8"));
      String line = null;
      line = invoiceReader.readLine();
      int itemCount = 0;
      if (null != line && line.length() > 0) {
         itemCount = Integer.parseInt(line);
         invoices = new Invoice[itemCount];
      } else {
         invoiceReader.close();
         throw new IOException("Could not read the invoice file");
      }
      itemCount = 0;
      while ((line = invoiceReader.readLine()) != null && line.length() > 0) {
         String[] items = line.split(",");
         invoices[itemCount++] = new Invoice(Integer.parseInt(items[0]), Integer.parseInt(items[1]), Long.parseLong(items[2]));
      }
      invoiceReader.close();
      BufferedReader paymentsReader = new BufferedReader(new InputStreamReader(new FileInputStream(paymentsFile), "UTF-8"));
      line = paymentsReader.readLine();
      itemCount = 0;
      if (null != line && line.length() > 0) {
         itemCount = Integer.parseInt(line);
         payments = new Payment[itemCount];
      } else {
         paymentsReader.close();
         throw new IOException("Could not read the invoice file");
      }
      itemCount = 0;
      while ((line = paymentsReader.readLine()) != null && line.length() > 0) {
         String[] items = line.split(",");
         payments[itemCount++] = new Payment(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
      }
      paymentsReader.close();
   }

   /**
    * A naive simple implementation handling the creation of new invoices based on
    * old invoices and payments received from customers.
    * 
    * @throws IOException
    */
   public void handleInvoicesAndPaymentsSlow() throws IOException {
      for (int counter = 0; counter < invoices.length; counter++) {
         Invoice invoice = invoices[counter];
         boolean noPaymentForInvoiceFound = true;
         Calendar dueDate = Calendar.getInstance();
         dueDate.set(Calendar.MONTH, dueDate.get(Calendar.MONTH)+1);
         long dueDateValue = dueDate.getTime().getTime();
         for (int paymentCounter = 0; paymentCounter < payments.length; paymentCounter++) {
            Payment payment = payments[paymentCounter];
            if (invoice.number.compareTo(payment.number) == 0) {
               noPaymentForInvoiceFound = false;
               if (invoice.sum.compareTo(payment.sum) > 0) {
                  toCollect.add(new Invoice(invoice.number, invoice.sum - payment.sum, dueDateValue));
                  break;
               }
            }
         }
         if (noPaymentForInvoiceFound) {
            toCollect.add(invoice);
         }
      }
      // Invoices and payments are unsorted so must first sort 
      // and then put back to list to be saved in order, as required.
      // Invoice[] array = new Invoice[toCollect.size()];
      Invoice[] array = new Invoice[toCollect.size()];
      int index = 0;
      for (Invoice invoice : toCollect) {
         array[index++] = invoice;
      }
      // NOTE: This is your Algorithms sort used here!
      Algorithms.sort(array);
      toCollect.clear();
      for (Invoice invoice : array) {
         toCollect.add(invoice);
      }
   }

   public void saveNewInvoices(String outputFile) throws IOException {
      BufferedWriter toCollectWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
      for (Invoice invoice : toCollect) {
         toCollectWriter.write(invoice.number.toString() + "," + invoice.sum.toString() + "," + invoice.dueDate.getTime());
         toCollectWriter.newLine();
      }
      toCollectWriter.close();
   }

   public void handleInvoicesAndPaymentsFast() {
      Calendar dueDate = Calendar.getInstance();
      dueDate.set(Calendar.MONTH, dueDate.get(Calendar.MONTH)+1);
      long dueDateValue = dueDate.getTime().getTime();

      Algorithms.fastSort(invoices);
      Algorithms.fastSort(payments);

      for (Invoice invoice : invoices) {
         // Try to find index of payment
         int index = Algorithms.binarySearch(new Payment(invoice.number, 0), payments, 0, payments.length - 1);
         if (index != -1) {
            if (invoice.sum.compareTo(payments[index].sum) > 0) {
               toCollect.add(new Invoice(invoice.number, invoice.sum - payments[index].sum, dueDateValue));
            }
         } else {
            toCollect.add(invoice);
         }
      }
   }

}
