package com.crack.jrebel.jar;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.junit.Test;

import com.zeroturnaround.licensing.UserLicense;

public class JrebelUserLicenseRewire {

	SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

	@Test
	public void testKey() {
		try {

			UserLicense userLicense = UserLicense
					.loadInstance(new File(System.getProperty("user.dir") + "/src/main/resources/jrebel.6.4.4/jrebel_lic/jrebel2.lic"));

			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(userLicense.getLicense()));
			HashMap dataMap = (HashMap) ois.readObject();
			Iterator it = dataMap.entrySet().iterator();

			System.out.println("-----old data----------------------------------------");

			while (it.hasNext()) {
				Object key = it.next();
				System.out.println(key);
			}

			dataMap.remove("GeneratedOn");
			dataMap.remove("limitedFrom");
			dataMap.remove("validFrom");

			dataMap.put("GeneratedOn", "Sat May 20 23:57:00 CST 2017");
			dataMap.put("limitedFrom", "Sat May 20 23:57:00 CST 2017");
			dataMap.put("validFrom", "Sat May 20 23:57:00 CST 2017");

			dataMap.remove("override");
			dataMap.put("override", "true");

			dataMap.remove("validDays");
			dataMap.put("validDays", "999");

			System.out.println("-----new data----------------------------------------");

			it = dataMap.entrySet().iterator();

			while (it.hasNext()) {
				Object key = it.next();
				System.out.println(key);
			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(dataMap);
			byte[] licenseBuf = bos.toByteArray();

			System.out.println("[LOG]" + licenseBuf.length);

			userLicense.setLicense(licenseBuf);
			//簽名：
			userLicense.setSignature(licenseBuf);

			ObjectOutputStream oosss = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream("C:/Users/tommy/Desktop/jrebel.lic")));
			oosss.writeObject(userLicense);
			oosss.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void dateFormat() throws ParseException {
		System.out.println("-----------------------------");
		String stringDate = "Sat May 21 02:26:41 CST 2016";
		System.out.println(stringDate);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		Date date = sdf.parse(stringDate);
		sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		System.out.println(sdf.format(date));

		date = sdf.parse("2026-05-2102:26:41");

		sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		System.out.println(sdf.format(date));

	}
}
