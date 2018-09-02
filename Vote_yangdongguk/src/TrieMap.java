import java.util.Comparator;
import java.util.LinkedList;

/**
 * project name : Vote_yangdongguk
 * author : Dongguk Yang
 * 
 * class TrieMap
 * 자바에서 제공하는 해시맵을 사용하지 않고 문제를 해결하기 위해 만든 클래스입니다.
 * 기본 아이디어는 Trie 자료구조와 유사하게 문자에서 문자로 이어지는 트리형태로 구성했습니다.
 * 다른점은 문자의 아스키코드값을 배열의 인덱스로 삼아 해당 문자에 대응하는 배열 원소에 접근할 수 있도록 했습니다. 
 * 
 * - int char_val : 자신의 문자값입니다.
 * - int count : 여기서 끝나는 문자열의 개수입니다. 
 * - TrieMap parent : 이전 문자로 가기 위한 참조 변수입니다.
 * - TrieMap[] child : 다음 문자로 가기 위한 참조 배열입니다.
 * - int MAXIMUM_CAPACITY : child 배열의 크기입니다. 127은 2진수로 1111111을 나타냅니다.
 * - LinkedList<TrieMap> referenceList : 문자열이 끝나는 지점의 참조값만 저장하는 리스트입니다.
 * 
 * - void put(String str) : 문자열을 TrieMap에 추가합니다.
 * - String getString() : 현재 위치에서 parent를 통해 올라가 문자열을 완성하고 반환합니다.
 * - LinkedList<String> voteResult() : 전체 TreeMap 중 가장 큰 count값을 가진 TreeMap의 String을 모아서 반환합니다.
 */

public class TrieMap {
	private int char_val;
	private int count;
	private TrieMap parent;
	private TrieMap[] child;
	private static final int MAXIMUM_CAPACITY = 127;
	private LinkedList<TrieMap> referenceList = new LinkedList<TrieMap>();

	public TrieMap() {
		child = new TrieMap[MAXIMUM_CAPACITY];
	}
	private TrieMap(TrieMap parent, int index) {
		this.parent = parent;
		this.char_val = index;
		child = new TrieMap[MAXIMUM_CAPACITY];
	}

	public void put(String str) {
		// 문자열의 길이만큼 TrieMap을 생성하고 이동하는 과정을 거칩니다.
		TrieMap curMap = this;
		for (int i = str.length() - 1; i > -1; --i) {
			int cIdx = str.charAt(i);
			if (curMap.child[cIdx] == null) {
				curMap.child[cIdx] = new TrieMap(curMap, cIdx);
			}
			curMap = curMap.child[cIdx];
		}
		// 문자열이 끝나는 지점을 추가합니다.
		if (curMap.count == 0)
			referenceList.add(curMap);
		// 끝나는 지점의 개수를 증가시킵니다.
		++curMap.count;
	}

	
	public String getString() {
		/*
		 * 참조변수 curMap을 이동하며 문자열을 완성합니다. 
		 * parent가 null일 경우 root노드이므로 종료합니다. 
		 */
		String str = "";
		TrieMap curMap = this;
		while (curMap.parent != null) {
			str += (char) curMap.char_val;
			curMap = curMap.parent;
		}
		return str;
	}

	public LinkedList<String> voteResult() {
		// referenceList를 count값이 큰 순서로 정렬합니다.
		referenceList.sort(new Comparator<TrieMap>() {
			@Override
			public int compare(TrieMap o1, TrieMap o2) {
				if (o1.count < o2.count)
					return 1;
				else if (o1.count > o2.count)
					return -1;
				else
					return 0;
			}
		});

		// 최대 count를 가진 문자열을 answer리스트에 저장합니다.
		LinkedList<String> answer = new LinkedList<String>();
		if (referenceList.size() > 0) {
			int max  = referenceList.get(0).count;
			answer.add(referenceList.get(0).getString());
			for (int i = 1; i < referenceList.size(); ++i) {
				if (referenceList.get(i).count < max)
					break;
				answer.add(referenceList.get(i).getString());
			}

			// 저장한 answer리스트를 알파벳 순으로 정렬합니다.
			answer.sort(new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
		}
		return answer;
	}
}
