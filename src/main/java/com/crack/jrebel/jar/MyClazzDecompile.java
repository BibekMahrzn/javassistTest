package com.crack.jrebel.jar;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class MyClazzDecompile {

	static final String packageNameAndDot = "com.zeroturnaround.javarebel.";

	/**
	 *
	 * <pre>
	 * Method Name : main
	 * Description : search RSADigestSigner
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// 6.4.4
		//		com.zeroturnaround.javarebel.eN;

		String clazzShortName = "eN";

		try {
			ClassPool pool = ClassPool.getDefault();
			//取得需要反編譯的jar檔，設定路徑
			pool.insertClassPath(System.getProperty("user.dir") + "/src/main/resources/jrebel.6.4.4/jrebel_lic/jrebel.jar");
			//取得需要反編譯修改的檔，注意是完整路徑
			CtClass cc1 = pool.get(packageNameAndDot + clazzShortName);
			//5.6.2 CtClass cc1 = pool.get("com.zeroturnaround.javarebel.tP");
			CtClass[] params = new CtClass[2];
			//方法對應的參數


			params[0] = pool.get("com.zeroturnaround.jrebel.bundled.org.bouncycastle.crypto.params.RSAKeyParameters");
			params[1] = pool.get("com.zeroturnaround.licensing.UserLicense");
			//取得需要修改的方法
			CtMethod method = cc1.getDeclaredMethod("a", params);

			CtClass[] parameterTypes = method.getParameterTypes();
			for (int i = 0; i < parameterTypes.length; i++) {
				System.out.println(parameterTypes[i]);
			}

			System.out.println(parameterTypes.length);
			//插入修改項，我們讓他直接返回true(注意：根據方法的具體返回值返回，因為這個方法返回值是boolean，所以直接return true;)
			method.insertBefore("if(2!=0) return true;");
			//寫入保存

			cc1.writeFile("C:/Users/tommy/Desktop/" + clazzShortName + ".class");
			//jar uvf jrebel.jar com/zeroturnaround/javarebel/tR.class
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
