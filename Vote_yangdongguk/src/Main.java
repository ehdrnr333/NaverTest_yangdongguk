import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		for (int i = 1; i <= 11; ++i) {
			// txt file read
			LinkedList<String> inputList = listFromTxt("Vote_testcase/" + i + ".input.txt");
			LinkedList<String> outputList = listFromTxt("Vote_testcase/" + i + ".output.txt");

			// print txt file answer
			System.out.println("Case " + i + ".");
			System.out.print("output : ");
			for (int j = 0; j < outputList.size(); ++j) {
				System.out.print(outputList.get(j) + " ");
			}
			System.out.println();

			// solve case
			long start = System.currentTimeMillis();
			LinkedList<String> result = voteResult(inputList);
			long end = System.currentTimeMillis();
			double response_time = (end - start) / 1000.0;

			// print result
			System.out.print("result : ");
			for (int j = 0; j < result.size(); ++j) {
				System.out.print(result.get(j) + " ");
			}
			System.out.println();
			System.out.println("Response Time : " + response_time);
		}
	}

	public static LinkedList<String> voteResult(LinkedList<String> list) {
		HashMap<String, Integer> nameCount = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); ++i) {
			String name = list.get(i);
			if (nameCount.containsKey(name)) {
				nameCount.put(name, nameCount.get(name) + 1);
			} else {
				nameCount.put(name, 1);
			}
		}

		int max = 0;
		LinkedList<String> answer = null;
		for (Map.Entry<String, Integer> entry : nameCount.entrySet()) {
			int value = entry.getValue();
			if (value > max) {
				answer = new LinkedList<String>();
				answer.add(entry.getKey());
				max = value;
			} else if (value == max) {
				answer.add(entry.getKey());
			}
		}
		return answer;
	}

	public static LinkedList<String> listFromTxt(String fileName) {
		LinkedList<String> list = new LinkedList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				list.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}