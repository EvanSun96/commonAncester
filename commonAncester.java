//common ancester related problems
//q1
public class Solution {
  public static List<Integer> find0or1(int[][] graph) {
    // calculate all the in degree of each node in graph, and add nodes with in degree is 0 or 1;
    //we use hashmap. first, in order to find node in degree is 1, we have to iterate graph[i][1] to calculate the number it appears. as for in degree, we need to check every graph[i][0] to see if it is in hashmap, if it is not, then this node definitly is 0 in degree.
    List<Integer> res = new ArrayList<>();
    if (graph == null || graph.length == 0 || graph[0].length == 0) {
      return res;
    }

    HashMap<Integer, Integer> map = new HashMap<>();//key is node, and value is the number of times it appears in graph[i][1];
    int m = graph.length;
    for (int i = 0; i < m; i++) {
      map.put(graph[i][1], map.getOrDefault(graph[i][1], 0) + 1);
    }
    System.out.println(Arrays.asList(map));
    //add all in degree is 1
    for (Integer node: map.keySet()) {
      //Integer value = 1;
      if (map.get(node) == 1){
        res.add(node);
      }
    }
    //now add in degree is 0
    HashSet<Integer> set = new HashSet<>(); //use hashset to remove duplicates
    for (int i = 0; i < m; i++) {
      if (!map.containsKey(graph[i][0])) {
        if (!set.contains(graph[i][0])) {
          set.add(graph[i][0]);
          res.add(graph[i][0]);
        }

      }
    }
    return res;
  }

  public static void main(String[] args) {
    int[][] graph = {{1,4}, {1,5}, {2,5}, {3,6}, {6,7}};
    System.out.println(Arrays.toString(find0or1(graph).toArray()));
  }
}

//q2
public class Solution {
  public static boolean common(int[][] graph, int node1, int node2) {

    //can we make sure that each node in graph has a differnet data?
    //let's assume node1 and node2 are valid

    //if node1 equals to node2
    if (node1 == node2) {
      return true;
    }

    //let's use the hashmap to store child and its directed parents
    HashMap<Integer, List<Integer>> map = new HashMap<>();
    for (int i = 0; i < graph.length; i++) {
      if (!map.containsKey(graph[i][1])) { //if we havn't have this key
        List<Integer> list = new ArrayList<>();
        list.add(graph[i][0]);
        map.put(graph[i][1], list);
      } else { //if we already have this key
        map.get(graph[i][1]).add(graph[i][0]);
      }
    }

    //another special conner case: when node1 or node 2 has no parent node
    if (!map.containsKey(node1) || !map.containsKey(node2)) {
      return false;
    }

    //let's find all the anscters of node1 and node2
    List<Integer> node1Parent = findAncesters(map, node1);
    List<Integer> node2Parent = findAncesters(map, node2);

    //if those two list contains the same node, then return true, or else return false
    HashSet<Integer> set = new HashSet<>();
    for (Integer parent: node1Parent) {
      set.add(parent);
    }
    for (Integer parent: node2Parent) {
      if (set.contains(parent)) {
        return true;
      }
    }
    return false;

  }

  private static List<Integer> findAncesters(HashMap<Integer, List<Integer>> map, int node) { //use bfs

    List<Integer> nodeParent = new ArrayList<>();

    Integer key = node;
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(node);
    while (!queue.isEmpty()) { //use bfs, like level order tranverse in tree
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        key = queue.poll();
        //we donot necessary have this key in map
        //if we don;t have, that means we've reached the conner of
        if (!map.containsKey(key)) {
          continue;
        }
        List<Integer> parentList = map.get(key);
        for (int j = 0; j < parentList.size(); j++) {
          queue.offer(parentList.get(i));
          nodeParent.add(parentList.get(i));
        }

      }
    }
    return nodeParent;

  }

  public static void main(String[] args) {
    int[][] graph = {{1,4}, {1,5}, {2,5}, {3,6}, {6,7}, {3, 8}};
    System.out.println(common(graph, 7, 8));
  }
}

//q3 modify q2
public class Solution {
  public static Integer common(int[][] graph, int node) {

    //can we make sure that each node in graph has a differnet data?
    //let's assume node1 and node2 are valid

    //let's use the hashmap to store child and its directed parents
    HashMap<Integer, List<Integer>> map = new HashMap<>();
    for (int i = 0; i < graph.length; i++) {
      if (!map.containsKey(graph[i][1])) { //if we havn't have this key
        List<Integer> list = new ArrayList<>();
        list.add(graph[i][0]);
        map.put(graph[i][1], list);
      } else { //if we already have this key
        map.get(graph[i][1]).add(graph[i][0]);
      }
    }

    //another special conner case: when node1 or node 2 has no parent node
    if (!map.containsKey(node)) {
      return node;
    }

    //let's find all the anscters of node1 and node2
    Integer furthestNode = findAncesters(map, node);

    return furthestNode;
  }

  private static Integer findAncesters(HashMap<Integer, List<Integer>> map, int node) { //use bfs

    List<Integer> nodeParent = new ArrayList<>();

    Integer key = node;
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(node);
    while (!queue.isEmpty()) { //use bfs, like level order tranverse in tree
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        key = queue.poll();
        //we donot necessary have this key in map
        //if we don;t have, that means we've reached the conner of
        if (!map.containsKey(key)) {
          continue;
        }
        List<Integer> parentList = map.get(key);
        for (int j = 0; j < parentList.size(); j++) {
          queue.offer(parentList.get(i));
          nodeParent.add(parentList.get(i));
        }

      }
    }
    return nodeParent.get(nodeParent.size()  -1); //get the last one if nodeParent

  }

  public static void main(String[] args) {
    int[][] graph = {{1,4}, {1,5}, {2,5}, {3,6}, {6,7}, {3, 8}, {10, 1}, {10, 2}, {10, 3}};
    System.out.println(common(graph, 8));
  }
}
