package com.example.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.text.Document;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.html2pdf.HtmlConverter;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@SpringBootApplication
public class QrcodeApplication {
	final static String ICCID_PREFIX = "898521220080849";

	public static void main(String[] args) throws IOException {
		SpringApplication.run(QrcodeApplication.class, args);

		for (int i = 1000; i <= 1001; i++) {
			String randomString = "8888" + getRandomString(1, true);
			String iccid = ICCID_PREFIX + i + getLuhnCheckDigit(ICCID_PREFIX + i);

			String iccidString = "iccid=" + iccid + "&passcode=" + randomString;

			System.out.print(iccidString);
			System.out.print("\n");

			generateQRcode(iccidString, 200, "png");
			// System.out.print(qrcodeStr);

			generateQRcodeToCsv(iccid + "," + randomString);
			System.out.print("\n");

			//addImageToPdfusingHtml("./convertpdf.html");
		}

	}

	/**
	 * Get iccid check digit
	 * 
	 * @param iccid
	 * @return check digit
	 */
	@GetMapping("/getIccidCheckDigit")
	public static String getLuhnCheckDigit(@RequestParam(value = "iccid") String iccid) {
		if (iccid == null)
			return null;
		String digit;
		/* convert to array of int for simplicity */
		int[] digits = new int[iccid.length()];
		for (int i = 0; i < iccid.length(); i++) {
			digits[i] = Character.getNumericValue(iccid.charAt(i));
		}

		/* double every other starting from right - jumping from 2 in 2 */
		for (int i = digits.length - 1; i >= 0; i -= 2) {
			digits[i] += digits[i];

			/* taking the sum of digits grater than 10 - simple trick by substract 9 */
			if (digits[i] >= 10) {
				digits[i] = digits[i] - 9;
			}
		}
		int sum = 0;
		for (int i = 0; i < digits.length; i++) {
			sum += digits[i];
		}
		/* multiply by 9 step */
		sum = sum * 9;

		/* convert to string to be easier to take the last digit */
		digit = sum + "";
		return digit.substring(digit.length() - 1);
	}

	public static String getRandomString(int length, boolean isNumber) {
		String chars = "";
		if (isNumber) {
			chars = "1234567890";
		} else {
			chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		}
		StringBuilder random = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = (int) (chars.length() * Math.random());
			random.append(chars.charAt(index));
		}
		return random.toString();
	}

	@GetMapping("/generateQRcode")
	public static File generateQRcode(@RequestParam(value = "qrcodeStr") String qrcodeStr,
			@RequestParam(value = "qrcodeWidth") int qrcodeWidth, @RequestParam(value = "fileType") String fileType) {

		File qrcode = new File("C:\\Users\\pt4113\\Desktop\\test\\" + "QRcode" + "_" + qrcodeStr + ".png");
		try {

			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrcodeStr, BarcodeFormat.QR_CODE, qrcodeWidth, qrcodeWidth);

			Path path = FileSystems.getDefault()
					.getPath("C:\\Users\\pt4113\\Desktop\\test\\" + "QRcode" + "_" + qrcodeStr + ".png");
			MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

			if (qrcode.createNewFile()) {
				System.out.println("File created: " + qrcode.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return qrcode;

	}

	@GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getImageWithMediaType() throws IOException {
		InputStream in = getClass().getResourceAsStream(
				"C:\\Users\\pt4113\\Desktop\\test\\QRcode_iccid=89852122008084910003&passcode=88889.png");
		System.out.print(in);
		return IOUtils.toByteArray(in);
	}
	
	@GetMapping("/getImage")
	public ModelAndView getImage() throws IOException, URISyntaxException {
		BufferedImage bImage = ImageIO.read(new File("QRcode_iccid=89852122008084910003&passcode=88889.png"));
		System.out.print(bImage);
		System.out.print("\n");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bImage, "jpg", bos);
		byte[] data = bos.toByteArray();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("abc",
				"C:\\Users\\pt4113\\Desktop\\test\\QRcode_iccid=89852122008084910003&passcode=88889.png");
		return modelAndView;
	}

	public static void generateQRcodeToCsv(String qrcodeStr) {
		File qrcodeFile = new File("C:\\Users\\pt4113\\Desktop\\test\\QRcodeFile.csv");
		try {

			FileWriter qrcodeFileWriter = new FileWriter(qrcodeFile, true);
			qrcodeFileWriter.write(qrcodeStr + "\n");
			qrcodeFileWriter.close();

			if (qrcodeFile.createNewFile()) {
				System.out.println("File created: " + qrcodeFile.getName());
			} else {
				System.out.println("File already exists.");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

//	public static void generateQRcodeToPdf() {
//
//		Document document = new Document();
//		String input = "C:\\Users\\pt4113\\Desktop\\test\\QRcode_iccid=89852122008084910003&passcode=88880.png";
//		String output = "C:\\Users\\pt4113\\Desktop\\test\\qrcode.pdf";
//		try {
//			FileOutputStream fos = new FileOutputStream(output);
//			PdfWriter writer = PdfWriter.getInstance(document, fos);
//			writer.open();
//			document.open();
//			document.add(Image.getInstance(input));
//			document.close();
//			writer.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		qrcodeDocument document = new qrcodeDocument(PageSize.A4, 20, 20, 20, 20);
//		PdfWriter.getInstance((com.itextpdf.text.Document) document,
//				new FileOutputStream("C:\\Users\\pt4113\\Desktop\\test\\qrcode.pdf"));
//		document.open();
//		Image image = Image.getInstance(getClass()
//				.getResource("C:\\Users\\pt4113\\Desktop\\test\\QRcode_iccid=89852122008084910003&passcode=88880.png"));
//		document.add(image);
//		document.close();
//	}

//	public static void generateQRcodeToPdf() {
//
//		Document document = new Document(PageSize.A4, 20, 20, 20, 20);
//		PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\pt4113\\Desktop\\test\\qrcode.pdf"));
//		document.open();
//		Image image = Image.getInstance(getClass().getResource("C:\\Users\\pt4113\\Desktop\\test\\QRcode_iccid=89852122008084910003&passcode=88880.png"));
//		document.add(image);
//		document.close();
//	}

//    public static void addImageToPdf(String srcPdf, String destPdf, String imagePath) throws IOException, DocumentException {
//        PdfReader reader = new PdfReader(srcPdf);
//        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destPdf));
//        PdfContentByte content = stamper.getOverContent(1);
//
//        Image image = Image.getInstance(imagePath);
//
//        // scale the image to 50px height
//        image.scaleAbsoluteHeight(50);
//        image.scaleAbsoluteWidth((image.getWidth() * 50) / image.getHeight());
//
//        image.setAbsolutePosition(70, 140);
//        content.addImage(image);
//
//        stamper.close();
//    }	

//	public static void addImageToPdfusingHtml(String fileType) throws IOException {
//		HtmlConverter.convertToPdf(new File(fileType), new File("Qrcode.pdf"));
//	}
	

	

}
