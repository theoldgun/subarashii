package cutFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class cutFile {
	// C:\Users\PC090\Desktop\ChoiceTest7-s02.2_e01_new_20170515.jtl
	public static void readAndWrite(String readPath, String nums) throws Exception {
		File inPutFile = new File(readPath);
		BufferedReader reader = new BufferedReader(new FileReader(inPutFile));

		String line1 = reader.readLine();
		String line2 = reader.readLine();
		String end = "\n</testResults>";

		String tempString = null;
		FileWriter writer = null;
		FileWriter writerErr = null;
		
		writerErr = mikDir("error.jtl");
		for (int i = 0; i <  Integer.parseInt(nums); i++) {
			if (writer == null) {
				writer = mikDir(i + ".jtl");
				WriteHeader(line1, line2, writer);
			}

			while ((tempString = reader.readLine()) != null) {
				if (tempString.contains("rc=\"200\"")){
					if (tempString.contains((i + 2) + "-1")) {
						writer.write(end);
						writer.close();
						writer = mikDir((i + 1) + ".jtl");
						WriteHeader(line1, line2, writer);
						writer.write(tempString + "\n");
						break;
					}
					writer.write(tempString + "\n");
				}else{
					writerErr.write(tempString + "\n");
				}
			}
		}
		writerErr.close();
		writer.close();
		reader.close();
		System.out.println("output files end");
	}

	private static void WriteHeader(String line1, String line2, FileWriter writer) throws IOException {
		writer.write(line1 + "\n");
		writer.write(line2 + "\n");
	}

	public static FileWriter mikDir(String writeName) throws IOException {
		File outPutFile = new File(writeName);
		FileWriter writer = new FileWriter(outPutFile);
		if (!outPutFile.exists()) {
			outPutFile.createNewFile();
		}
		return writer;
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("parameter not enough");
			System.exit(1);
		}
		System.out.println("readPath:" + args[0] + "fileNums" + args[1]);
		readAndWrite(args[0], args[1]);
	}
}
