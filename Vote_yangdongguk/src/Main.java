import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		for (int i = 1; i <= 11; ++i) {
			// output txt file read and print
			LinkedList<String> outputList = listFromTxt("Vote_testcase/" + i + ".output.txt");
			System.out.println("Case " + i + ".");
			System.out.print("output : ");
			for (int j = 0; j < outputList.size(); ++j) {
				System.out.print(outputList.get(j) + " ");
			}
			System.out.println();

			// input txt file read and solve problem
			long start = System.currentTimeMillis();
			HashMap<String, Integer> inputHashMap = hashmapFromTxt("Vote_testcase/" + i + ".input.txt");
			LinkedList<String> result = voteResult(inputHashMap);
			long end = System.currentTimeMillis();
			double response_time = (end - start) / 1000.0;

			// print result
			System.out.print("result : ");
			for (int j = 0; j < result.size(); ++j)
				System.out.print(result.get(j) + " ");
			System.out.println();
			System.out.println("Response Time : " + response_time);
		}
	}

	private static LinkedList<String> voteResult(HashMap<String, Integer> inputHashMap) {
		int max = 0;
		LinkedList<String> answer = new LinkedList<String>();
		for (Map.Entry<String, Integer> entry : inputHashMap.entrySet()) {
			int value = entry.getValue();
			if (value > max) {
				answer.clear();
				answer.add(entry.getKey());
				max = value;
			} else if (value == max) {
				answer.add(entry.getKey());
			}
		}
		answer.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}

		});
		return answer;
	}

	public static HashMap<String, Integer> hashmapFromTxt(String fileName) {
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				if (hashMap.containsKey(line)) {
					hashMap.put(line, hashMap.get(line) + 1);
				} else {
					hashMap.put(line, 1);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashMap;
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