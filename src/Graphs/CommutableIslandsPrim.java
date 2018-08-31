package Graphs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class CommutableIslandsPrim {

    // Minimum Spanning Tree

    // O(V ^ 2) time
    public int solve(int A, int[][] B) {
        Edge[] edges = new Edge[B.length];
        for (int i = 0; i < B.length; i++) {
            int v1 = B[i][0] - 1;
            int v2 = B[i][1] - 1;
            int w = B[i][2];
            edges[i] = new Edge(v1, v2, w);
        }

        boolean[] visitedSet = new boolean[A];
        int[] keyValues = new int[A];

        for (int i = 0; i < A; i++) {
            visitedSet[i] = false;
            keyValues[i] = Integer.MAX_VALUE;
        }
        keyValues[0] = 0;

        int result = 0;
        for (int i = 0; i < A; i++) {
            int minVertex = getMinUnvisitedKeyValue(visitedSet, keyValues);
            visitedSet[minVertex] = true;
            updateAdjacentVertices(minVertex, edges, keyValues, visitedSet);
            result += keyValues[minVertex];
        }
        return result;
    }

    // O(E * logV) time
    public int solve2(int A, int[][] B) {
        Edge[] edges = new Edge[B.length];
        for (int i = 0; i < B.length; i++) {
            int v1 = B[i][0] - 1;
            int v2 = B[i][1] - 1;
            int w = B[i][2];
            edges[i] = new Edge(v1, v2, w);
        }

        boolean[] visitedSet = new boolean[A];
        Node[] nodes = new Node[A];
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.keyValue - o2.keyValue;
            }
        });
        ArrayList<Node>[] adjacentNodes = new ArrayList[A];

        for (int i = 0; i < A; i++) {
            visitedSet[i] = false;
            nodes[i] = new Node(i, i == 0 ? 0 : Integer.MAX_VALUE);
            priorityQueue.add(nodes[i]);
            adjacentNodes[i] = new ArrayList<>();
        }

        for (Edge e : edges) {
            adjacentNodes[e.src].add(new Node(e.dest, e.weight));
            adjacentNodes[e.dest].add(new Node(e.src, e.weight));
        }

        int result = 0;
        for (int i = 0; i < A; i++) {
            Node min = priorityQueue.poll();
            visitedSet[min.vertex] = true;
            updateAdjacentVertices(adjacentNodes[min.vertex], priorityQueue, visitedSet, nodes);
            result += min.keyValue;
        }
        return result;
    }

    private int getMinUnvisitedKeyValue(boolean[] visitedSet, int[] keyValues) {
        int minId = -1;
        for (int i = 0; i < keyValues.length; i++) {
            if (!visitedSet[i] && (minId == -1 || keyValues[i] < keyValues[minId])) {
                minId = i;
            }
        }
        return minId;
    }

    private void updateAdjacentVertices(int v, Edge[] edges, int[] keyValues, boolean[] visitedSet) {
        for (Edge edge : edges) {
            if (edge.src == v && !visitedSet[edge.dest]) {
                if (edge.weight < keyValues[edge.dest]) {
                    keyValues[edge.dest] = edge.weight;
                }
            }
            if (edge.dest == v && !visitedSet[edge.src]) {
                if (edge.weight < keyValues[edge.src]) {
                    keyValues[edge.src] = edge.weight;
                }
            }
        }
    }

    private void updateAdjacentVertices(
            ArrayList<Node> adjacentNodes,
            PriorityQueue<Node> priorityQueue,
            boolean[] visitedSet,
            Node[] nodes
    ) {
        for (Node n : adjacentNodes) {
            if (!visitedSet[n.vertex]) {
                if (n.keyValue < nodes[n.vertex].keyValue) {
                    priorityQueue.remove(nodes[n.vertex]);
                    nodes[n.vertex].keyValue = n.keyValue;
                    priorityQueue.add(nodes[n.vertex]);
                }
            }
        }
    }

    class Node {
        int vertex;
        int keyValue;

        Node(int vertex, int keyValue) {
            this.vertex = vertex;
            this.keyValue = keyValue;
        }
    }

    public static void main(String[] args) {
        CommutableIslandsPrim instance = new CommutableIslandsPrim();

        String input = "[7, 65, 610]\n" +
                "  [74, 53, 927]\n" +
                "  [68, 67, 263]\n" +
                "  [67, 56, 277]\n" +
                "  [17, 34, 275]\n" +
                "  [66, 42, 822]\n" +
                "  [24, 73, 984]\n" +
                "  [39, 19, 324]\n" +
                "  [76, 45, 820]\n" +
                "  [12, 78, 488]\n" +
                "  [35, 29, 177]\n" +
                "  [25, 52, 682]\n" +
                "  [47, 42, 192]\n" +
                "  [4, 24, 783]\n" +
                "  [7, 44, 255]\n" +
                "  [75, 64, 73]\n" +
                "  [13, 60, 979]\n" +
                "  [5, 65, 131]\n" +
                "  [71, 5, 443]\n" +
                "  [33, 39, 966]\n" +
                "  [17, 58, 9]\n" +
                "  [48, 15, 102]\n" +
                "  [30, 55, 807]\n" +
                "  [23, 14, 961]\n" +
                "  [26, 74, 1000]\n" +
                "  [75, 9, 433]\n" +
                "  [50, 18, 660]\n" +
                "  [32, 73, 320]\n" +
                "  [38, 56, 17]\n" +
                "  [43, 11, 292]\n" +
                "  [1, 19, 919]\n" +
                "  [21, 52, 172]\n" +
                "  [77, 26, 91]\n" +
                "  [27, 22, 666]\n" +
                "  [32, 44, 575]\n" +
                "  [25, 13, 490]\n" +
                "  [64, 31, 752]\n" +
                "  [18, 41, 794]\n" +
                "  [36, 51, 801]\n" +
                "  [51, 63, 187]\n" +
                "  [36, 9, 959]\n" +
                "  [2, 8, 855]\n" +
                "  [10, 43, 596]\n" +
                "  [33, 70, 660]\n" +
                "  [59, 46, 56]\n" +
                "  [23, 57, 161]\n" +
                "  [49, 31, 54]\n" +
                "  [76, 5, 223]\n" +
                "  [68, 28, 123]\n" +
                "  [10, 28, 26]\n" +
                "  [27, 66, 909]\n" +
                "  [20, 11, 649]\n" +
                "  [59, 58, 329]\n" +
                "  [45, 59, 186]\n" +
                "  [53, 40, 606]\n" +
                "  [54, 12, 370]\n" +
                "  [69, 61, 865]\n" +
                "  [57, 72, 572]\n" +
                "  [37, 48, 601]\n" +
                "  [29, 60, 872]\n" +
                "  [63, 47, 77]\n" +
                "  [15, 40, 295]\n" +
                "  [16, 45, 330]\n" +
                "  [34, 3, 652]\n" +
                "  [54, 37, 482]\n" +
                "  [6, 37, 533]\n" +
                "  [30, 41, 146]\n" +
                "  [62, 50, 514]\n" +
                "  [8, 57, 492]\n" +
                "  [22, 70, 543]\n" +
                "  [78, 2, 581]\n" +
                "  [35, 61, 750]\n" +
                "  [49, 69, 71]\n" +
                "  [14, 55, 135]\n" +
                "  [21, 4, 610]\n" +
                "  [77, 3, 666]\n" +
                "  [38, 62, 743]\n" +
                "  [2, 1, 188]\n" +
                "  [3, 1, 38]\n" +
                "  [4, 1, 281]\n" +
                "  [5, 1, 833]\n" +
                "  [6, 1, 530]\n" +
                "  [7, 1, 241]\n" +
                "  [8, 1, 584]\n" +
                "  [9, 1, 248]\n" +
                "  [10, 1, 191]\n" +
                "  [11, 1, 575]\n" +
                "  [12, 1, 231]\n" +
                "  [13, 1, 649]\n" +
                "  [14, 1, 823]\n" +
                "  [15, 1, 784]\n" +
                "  [16, 1, 468]\n" +
                "  [17, 1, 39]\n" +
                "  [18, 1, 762]\n" +
                "  [20, 1, 416]\n" +
                "  [21, 1, 880]\n" +
                "  [22, 1, 551]\n" +
                "  [23, 1, 585]\n" +
                "  [24, 1, 944]\n" +
                "  [25, 1, 992]\n" +
                "  [26, 1, 621]\n" +
                "  [27, 1, 36]\n" +
                "  [28, 1, 915]\n" +
                "  [29, 1, 965]\n" +
                "  [30, 1, 870]\n" +
                "  [31, 1, 470]\n" +
                "  [32, 1, 866]\n" +
                "  [33, 1, 604]\n" +
                "  [34, 1, 756]\n" +
                "  [35, 1, 143]\n" +
                "  [36, 1, 761]\n" +
                "  [37, 1, 183]\n" +
                "  [38, 1, 701]\n" +
                "  [39, 1, 681]\n" +
                "  [40, 1, 215]\n" +
                "  [41, 1, 235]\n" +
                "  [42, 1, 204]\n" +
                "  [43, 1, 844]\n" +
                "  [44, 1, 826]\n" +
                "  [45, 1, 456]\n" +
                "  [46, 1, 815]\n" +
                "  [47, 1, 451]\n" +
                "  [48, 1, 929]\n" +
                "  [49, 1, 404]\n" +
                "  [50, 1, 399]\n" +
                "  [51, 1, 5]\n" +
                "  [52, 1, 56]\n" +
                "  [53, 1, 928]\n" +
                "  [54, 1, 731]\n" +
                "  [55, 1, 60]\n" +
                "  [56, 1, 63]\n" +
                "  [57, 1, 745]\n" +
                "  [58, 1, 581]\n" +
                "  [59, 1, 241]\n" +
                "  [60, 1, 275]\n" +
                "  [61, 1, 240]\n" +
                "  [62, 1, 563]\n" +
                "  [63, 1, 830]\n" +
                "  [64, 1, 692]\n" +
                "  [65, 1, 459]\n" +
                "  [66, 1, 15]\n" +
                "  [67, 1, 482]\n" +
                "  [68, 1, 725]\n" +
                "  [69, 1, 34]\n" +
                "  [70, 1, 208]\n" +
                "  [71, 1, 569]\n" +
                "  [72, 1, 776]\n" +
                "  [73, 1, 938]\n" +
                "  [74, 1, 935]\n" +
                "  [75, 1, 810]\n" +
                "  [76, 1, 471]\n" +
                "  [77, 1, 606]\n" +
                "  [78, 1, 955]\n" +
                "  [3, 2, 375]\n" +
                "  [4, 2, 252]\n" +
                "  [5, 2, 121]\n" +
                "  [6, 2, 821]\n" +
                "  [7, 2, 776]\n" +
                "  [9, 2, 650]\n" +
                "  [10, 2, 429]\n" +
                "  [11, 2, 317]\n" +
                "  [12, 2, 584]\n" +
                "  [13, 2, 409]\n" +
                "  [14, 2, 319]\n" +
                "  [15, 2, 891]\n" +
                "  [16, 2, 447]\n" +
                "  [17, 2, 638]\n" +
                "  [18, 2, 104]\n" +
                "  [19, 2, 814]\n" +
                "  [20, 2, 358]\n" +
                "  [21, 2, 840]\n" +
                "  [22, 2, 69]\n" +
                "  [23, 2, 225]\n" +
                "  [24, 2, 766]\n" +
                "  [25, 2, 933]\n" +
                "  [26, 2, 208]\n" +
                "  [27, 2, 425]\n" +
                "  [28, 2, 401]\n" +
                "  [29, 2, 731]\n" +
                "  [30, 2, 750]\n" +
                "  [31, 2, 448]\n" +
                "  [32, 2, 615]\n" +
                "  [33, 2, 679]\n" +
                "  [34, 2, 335]\n" +
                "  [35, 2, 669]\n" +
                "  [36, 2, 227]\n" +
                "  [37, 2, 417]\n" +
                "  [38, 2, 249]\n" +
                "  [39, 2, 794]\n" +
                "  [40, 2, 308]\n" +
                "  [41, 2, 190]\n" +
                "  [42, 2, 429]\n" +
                "  [43, 2, 901]\n" +
                "  [44, 2, 725]\n" +
                "  [45, 2, 837]\n" +
                "  [46, 2, 833]\n" +
                "  [47, 2, 799]\n" +
                "  [48, 2, 44]\n" +
                "  [49, 2, 523]\n" +
                "  [50, 2, 212]\n" +
                "  [51, 2, 448]\n" +
                "  [52, 2, 246]\n" +
                "  [53, 2, 623]\n" +
                "  [54, 2, 740]\n" +
                "  [55, 2, 601]\n" +
                "  [56, 2, 314]\n" +
                "  [57, 2, 854]\n" +
                "  [58, 2, 619]\n" +
                "  [59, 2, 720]\n" +
                "  [60, 2, 634]\n" +
                "  [61, 2, 808]\n" +
                "  [62, 2, 534]\n" +
                "  [63, 2, 944]\n" +
                "  [64, 2, 189]\n" +
                "  [65, 2, 225]\n" +
                "  [66, 2, 157]\n" +
                "  [67, 2, 654]\n" +
                "  [68, 2, 512]\n" +
                "  [69, 2, 817]\n" +
                "  [70, 2, 974]\n" +
                "  [71, 2, 293]\n" +
                "  [72, 2, 248]\n" +
                "  [73, 2, 668]\n" +
                "  [74, 2, 979]\n" +
                "  [75, 2, 971]\n" +
                "  [76, 2, 113]\n" +
                "  [77, 2, 547]\n" +
                "  [4, 3, 575]\n" +
                "  [5, 3, 554]\n" +
                "  [6, 3, 610]\n" +
                "  [7, 3, 546]\n" +
                "  [8, 3, 436]\n" +
                "  [9, 3, 688]\n" +
                "  [10, 3, 431]\n" +
                "  [11, 3, 56]\n" +
                "  [12, 3, 504]\n" +
                "  [13, 3, 263]\n" +
                "  [14, 3, 814]\n" +
                "  [15, 3, 370]\n" +
                "  [16, 3, 755]\n" +
                "  [17, 3, 670]\n" +
                "  [18, 3, 202]\n" +
                "  [19, 3, 895]\n" +
                "  [20, 3, 850]\n" +
                "  [21, 3, 486]\n" +
                "  [22, 3, 756]\n" +
                "  [23, 3, 541]\n" +
                "  [24, 3, 440]\n" +
                "  [25, 3, 341]\n" +
                "  [26, 3, 468]\n" +
                "  [27, 3, 395]\n" +
                "  [28, 3, 464]\n" +
                "  [29, 3, 310]\n" +
                "  [30, 3, 612]\n" +
                "  [31, 3, 221]\n" +
                "  [32, 3, 492]\n" +
                "  [33, 3, 424]\n" +
                "  [35, 3, 862]\n" +
                "  [36, 3, 996]\n" +
                "  [37, 3, 393]\n" +
                "  [38, 3, 187]\n" +
                "  [39, 3, 105]\n" +
                "  [40, 3, 123]\n" +
                "  [41, 3, 224]\n" +
                "  [42, 3, 542]\n" +
                "  [43, 3, 808]\n" +
                "  [44, 3, 435]\n" +
                "  [45, 3, 87]\n" +
                "  [46, 3, 875]\n" +
                "  [47, 3, 274]\n" +
                "  [48, 3, 799]\n" +
                "  [49, 3, 526]\n" +
                "  [50, 3, 41]\n" +
                "  [51, 3, 299]\n" +
                "  [52, 3, 105]\n" +
                "  [53, 3, 123]\n" +
                "  [54, 3, 865]\n" +
                "  [55, 3, 810]\n" +
                "  [56, 3, 748]\n" +
                "  [57, 3, 966]\n" +
                "  [58, 3, 506]\n" +
                "  [59, 3, 977]\n" +
                "  [60, 3, 887]\n" +
                "  [61, 3, 341]\n" +
                "  [62, 3, 710]\n" +
                "  [63, 3, 289]\n" +
                "  [64, 3, 392]\n" +
                "  [65, 3, 489]\n" +
                "  [66, 3, 18]\n" +
                "  [67, 3, 531]\n" +
                "  [68, 3, 439]\n" +
                "  [69, 3, 256]\n" +
                "  [70, 3, 18]\n" +
                "  [71, 3, 407]\n" +
                "  [72, 3, 431]\n" +
                "  [73, 3, 689]\n" +
                "  [74, 3, 434]\n" +
                "  [75, 3, 425]\n" +
                "  [76, 3, 384]\n" +
                "  [78, 3, 875]\n" +
                "  [5, 4, 974]\n" +
                "  [6, 4, 311]\n" +
                "  [7, 4, 432]\n" +
                "  [8, 4, 750]\n" +
                "  [9, 4, 635]\n" +
                "  [10, 4, 180]\n" +
                "  [11, 4, 581]\n" +
                "  [12, 4, 832]\n" +
                "  [13, 4, 116]\n" +
                "  [14, 4, 761]\n" +
                "  [15, 4, 540]\n" +
                "  [16, 4, 137]\n" +
                "  [17, 4, 998]\n" +
                "  [18, 4, 471]\n" +
                "  [19, 4, 478]\n" +
                "  [20, 4, 287]\n" +
                "  [22, 4, 916]\n" +
                "  [23, 4, 589]\n" +
                "  [25, 4, 285]\n" +
                "  [26, 4, 219]\n" +
                "  [27, 4, 707]\n" +
                "  [28, 4, 880]\n" +
                "  [29, 4, 124]\n" +
                "  [30, 4, 579]\n" +
                "  [31, 4, 903]\n" +
                "  [32, 4, 310]\n" +
                "  [33, 4, 948]\n" +
                "  [34, 4, 86]\n" +
                "  [35, 4, 365]\n" +
                "  [36, 4, 394]\n" +
                "  [37, 4, 112]\n" +
                "  [38, 4, 651]\n" +
                "  [39, 4, 311]\n" +
                "  [40, 4, 644]\n" +
                "  [41, 4, 82]\n" +
                "  [42, 4, 794]\n" +
                "  [43, 4, 811]\n" +
                "  [44, 4, 846]\n" +
                "  [45, 4, 339]\n" +
                "  [46, 4, 737]\n" +
                "  [47, 4, 596]\n" +
                "  [48, 4, 79]\n" +
                "  [49, 4, 359]\n" +
                "  [50, 4, 480]\n" +
                "  [51, 4, 167]\n" +
                "  [52, 4, 996]\n" +
                "  [53, 4, 78]\n" +
                "  [54, 4, 565]\n" +
                "  [55, 4, 368]\n" +
                "  [56, 4, 282]\n" +
                "  [57, 4, 941]\n" +
                "  [58, 4, 873]\n" +
                "  [59, 4, 395]\n" +
                "  [60, 4, 942]\n" +
                "  [61, 4, 483]\n" +
                "  [62, 4, 226]\n" +
                "  [63, 4, 795]\n" +
                "  [64, 4, 933]\n" +
                "  [65, 4, 540]\n" +
                "  [66, 4, 583]\n" +
                "  [67, 4, 814]\n" +
                "  [68, 4, 724]\n" +
                "  [69, 4, 658]\n" +
                "  [70, 4, 599]\n" +
                "  [71, 4, 558]\n" +
                "  [72, 4, 825]\n" +
                "  [73, 4, 351]\n" +
                "  [74, 4, 922]\n" +
                "  [75, 4, 106]\n" +
                "  [76, 4, 191]\n" +
                "  [77, 4, 483]\n" +
                "  [78, 4, 590]\n" +
                "  [6, 5, 491]\n" +
                "  [7, 5, 423]\n" +
                "  [8, 5, 380]\n" +
                "  [9, 5, 339]\n" +
                "  [10, 5, 551]\n" +
                "  [11, 5, 162]\n" +
                "  [12, 5, 633]\n" +
                "  [13, 5, 240]\n" +
                "  [14, 5, 42]\n" +
                "  [15, 5, 303]\n" +
                "  [16, 5, 167]\n" +
                "  [17, 5, 36]\n" +
                "  [18, 5, 952]\n" +
                "  [19, 5, 739]\n" +
                "  [20, 5, 916]\n" +
                "  [21, 5, 501]\n" +
                "  [22, 5, 916]\n" +
                "  [23, 5, 916]\n" +
                "  [24, 5, 607]\n" +
                "  [25, 5, 61]\n" +
                "  [26, 5, 413]\n" +
                "  [27, 5, 654]\n" +
                "  [28, 5, 318]\n" +
                "  [29, 5, 119]\n" +
                "  [30, 5, 563]\n" +
                "  [31, 5, 414]\n" +
                "  [32, 5, 557]\n" +
                "  [33, 5, 895]\n" +
                "  [34, 5, 910]\n" +
                "  [35, 5, 529]\n" +
                "  [36, 5, 446]\n" +
                "  [37, 5, 15]\n" +
                "  [38, 5, 648]\n" +
                "  [39, 5, 230]\n" +
                "  [40, 5, 772]\n" +
                "  [41, 5, 915]\n" +
                "  [42, 5, 270]\n" +
                "  [43, 5, 424]\n" +
                "  [44, 5, 911]\n" +
                "  [45, 5, 918]\n" +
                "  [46, 5, 106]\n" +
                "  [47, 5, 565]\n" +
                "  [48, 5, 491]\n" +
                "  [49, 5, 914]\n" +
                "  [50, 5, 964]\n" +
                "  [51, 5, 977]\n" +
                "  [52, 5, 1000]\n" +
                "  [53, 5, 837]\n" +
                "  [54, 5, 996]\n" +
                "  [55, 5, 204]\n" +
                "  [56, 5, 243]\n" +
                "  [57, 5, 990]\n" +
                "  [58, 5, 506]\n" +
                "  [59, 5, 88]\n" +
                "  [60, 5, 964]\n" +
                "  [61, 5, 300]\n" +
                "  [62, 5, 287]\n" +
                "  [63, 5, 836]\n" +
                "  [64, 5, 839]\n" +
                "  [66, 5, 535]\n" +
                "  [67, 5, 697]\n" +
                "  [68, 5, 219]\n" +
                "  [69, 5, 515]\n" +
                "  [70, 5, 301]\n" +
                "  [72, 5, 395]\n" +
                "  [73, 5, 942]\n" +
                "  [74, 5, 149]\n" +
                "  [75, 5, 375]\n" +
                "  [77, 5, 962]\n" +
                "  [78, 5, 462]\n" +
                "  [7, 6, 686]\n" +
                "  [8, 6, 49]\n" +
                "  [9, 6, 841]\n" +
                "  [10, 6, 27]\n" +
                "  [11, 6, 417]\n" +
                "  [12, 6, 581]\n" +
                "  [13, 6, 606]\n" +
                "  [14, 6, 8]\n" +
                "  [15, 6, 430]\n" +
                "  [16, 6, 665]\n" +
                "  [17, 6, 379]\n" +
                "  [18, 6, 620]\n" +
                "  [19, 6, 690]\n" +
                "  [20, 6, 212]\n" +
                "  [21, 6, 918]\n" +
                "  [22, 6, 162]\n" +
                "  [23, 6, 789]\n" +
                "  [24, 6, 605]\n" +
                "  [25, 6, 489]\n" +
                "  [26, 6, 74]\n" +
                "  [27, 6, 626]\n" +
                "  [28, 6, 220]\n" +
                "  [29, 6, 370]\n" +
                "  [30, 6, 294]\n" +
                "  [31, 6, 994]\n" +
                "  [32, 6, 583]\n" +
                "  [33, 6, 980]\n" +
                "  [34, 6, 910]\n" +
                "  [35, 6, 697]\n" +
                "  [36, 6, 687]\n" +
                "  [38, 6, 773]\n" +
                "  [39, 6, 476]\n" +
                "  [40, 6, 360]\n" +
                "  [41, 6, 248]\n" +
                "  [42, 6, 918]\n" +
                "  [43, 6, 473]\n" +
                "  [44, 6, 201]\n" +
                "  [45, 6, 780]\n" +
                "  [46, 6, 902]\n" +
                "  [47, 6, 806]\n" +
                "  [48, 6, 95]\n" +
                "  [49, 6, 937]\n" +
                "  [50, 6, 127]\n" +
                "  [51, 6, 652]\n" +
                "  [52, 6, 643]\n" +
                "  [53, 6, 142]\n" +
                "  [54, 6, 119]\n" +
                "  [55, 6, 140]\n" +
                "  [56, 6, 352]\n" +
                "  [57, 6, 74]\n" +
                "  [58, 6, 25]\n" +
                "  [59, 6, 832]\n" +
                "  [60, 6, 624]\n" +
                "  [61, 6, 677]\n" +
                "  [62, 6, 778]\n" +
                "  [63, 6, 432]\n" +
                "  [64, 6, 484]\n" +
                "  [65, 6, 317]\n" +
                "  [66, 6, 884]\n" +
                "  [67, 6, 950]\n" +
                "  [68, 6, 384]\n" +
                "  [69, 6, 763]\n" +
                "  [70, 6, 422]\n" +
                "  [71, 6, 619]\n" +
                "  [72, 6, 332]\n" +
                "  [73, 6, 667]\n" +
                "  [74, 6, 201]\n" +
                "  [75, 6, 767]\n" +
                "  [76, 6, 604]\n" +
                "  [77, 6, 352]\n" +
                "  [78, 6, 189]\n" +
                "  [8, 7, 461]\n" +
                "  [9, 7, 129]\n" +
                "  [10, 7, 885]\n" +
                "  [11, 7, 746]\n" +
                "  [12, 7, 764]\n" +
                "  [13, 7, 319]\n" +
                "  [14, 7, 525]\n" +
                "  [15, 7, 824]\n" +
                "  [16, 7, 95]\n" +
                "  [17, 7, 45]\n" +
                "  [18, 7, 84]\n" +
                "  [19, 7, 511]\n" +
                "  [20, 7, 782]\n" +
                "  [21, 7, 199]\n" +
                "  [22, 7, 123]\n" +
                "  [23, 7, 655]\n" +
                "  [24, 7, 107]\n" +
                "  [25, 7, 415]\n" +
                "  [26, 7, 336]\n" +
                "  [27, 7, 122]\n" +
                "  [28, 7, 878]\n" +
                "  [29, 7, 504]\n" +
                "  [30, 7, 924]\n" +
                "  [31, 7, 405]\n" +
                "  [32, 7, 706]\n" +
                "  [33, 7, 18]\n" +
                "  [34, 7, 715]\n" +
                "  [35, 7, 75]\n" +
                "  [36, 7, 949]\n" +
                "  [37, 7, 601]\n" +
                "  [38, 7, 223]\n" +
                "  [39, 7, 497]\n" +
                "  [40, 7, 956]\n" +
                "  [41, 7, 174]\n" +
                "  [42, 7, 78]\n" +
                "  [43, 7, 483]\n" +
                "  [45, 7, 620]\n" +
                "  [46, 7, 956]\n" +
                "  [47, 7, 207]\n" +
                "  [48, 7, 54]\n" +
                "  [49, 7, 58]\n" +
                "  [50, 7, 868]\n" +
                "  [51, 7, 857]\n" +
                "  [52, 7, 428]\n" +
                "  [53, 7, 309]\n" +
                "  [54, 7, 138]\n" +
                "  [55, 7, 413]\n" +
                "  [56, 7, 531]\n" +
                "  [57, 7, 686]\n" +
                "  [58, 7, 349]\n" +
                "  [59, 7, 487]\n" +
                "  [60, 7, 342]\n" +
                "  [61, 7, 631]\n" +
                "  [62, 7, 275]\n" +
                "  [63, 7, 848]\n" +
                "  [64, 7, 586]\n" +
                "  [66, 7, 153]\n" +
                "  [67, 7, 298]\n" +
                "  [68, 7, 615]\n" +
                "  [69, 7, 251]\n" +
                "  [70, 7, 436]\n" +
                "  [71, 7, 672]\n" +
                "  [72, 7, 99]\n" +
                "  [73, 7, 388]\n" +
                "  [74, 7, 323]\n" +
                "  [75, 7, 101]\n" +
                "  [76, 7, 195]\n" +
                "  [77, 7, 466]\n" +
                "  [78, 7, 173]\n" +
                "  [9, 8, 796]\n" +
                "  [10, 8, 502]\n" +
                "  [11, 8, 2]\n" +
                "  [12, 8, 398]\n" +
                "  [13, 8, 654]\n" +
                "  [14, 8, 19]\n" +
                "  [15, 8, 447]\n" +
                "  [16, 8, 755]\n" +
                "  [17, 8, 646]\n" +
                "  [18, 8, 235]\n" +
                "  [19, 8, 581]\n" +
                "  [20, 8, 130]\n" +
                "  [21, 8, 133]\n" +
                "  [22, 8, 10]\n" +
                "  [23, 8, 632]\n" +
                "  [24, 8, 640]\n" +
                "  [25, 8, 504]\n" +
                "  [26, 8, 913]\n" +
                "  [27, 8, 198]\n" +
                "  [28, 8, 384]\n" +
                "  [29, 8, 805]\n" +
                "  [30, 8, 567]\n" +
                "  [31, 8, 465]\n" +
                "  [32, 8, 395]\n" +
                "  [33, 8, 674]\n" +
                "  [34, 8, 963]\n" +
                "  [35, 8, 62]\n" +
                "  [36, 8, 491]\n" +
                "  [37, 8, 950]\n" +
                "  [38, 8, 668]\n" +
                "  [39, 8, 360]\n" +
                "  [40, 8, 22]\n" +
                "  [41, 8, 438]\n" +
                "  [42, 8, 697]\n" +
                "  [43, 8, 261]\n" +
                "  [44, 8, 713]\n" +
                "  [45, 8, 145]\n" +
                "  [46, 8, 625]\n" +
                "  [47, 8, 408]\n" +
                "  [48, 8, 451]\n" +
                "  [49, 8, 613]\n" +
                "  [50, 8, 789]\n" +
                "  [51, 8, 414]\n" +
                "  [52, 8, 765]\n" +
                "  [53, 8, 440]\n" +
                "  [54, 8, 969]\n" +
                "  [55, 8, 897]\n" +
                "  [56, 8, 841]\n" +
                "  [58, 8, 311]\n" +
                "  [59, 8, 527]\n" +
                "  [60, 8, 936]\n" +
                "  [61, 8, 424]\n" +
                "  [62, 8, 394]\n" +
                "  [63, 8, 59]\n" +
                "  [64, 8, 941]\n" +
                "  [65, 8, 178]\n" +
                "  [66, 8, 678]\n" +
                "  [67, 8, 376]\n" +
                "  [68, 8, 965]\n" +
                "  [69, 8, 58]\n" +
                "  [70, 8, 858]\n" +
                "  [71, 8, 409]\n" +
                "  [72, 8, 980]\n" +
                "  [73, 8, 136]\n" +
                "  [74, 8, 114]\n" +
                "  [75, 8, 817]\n" +
                "  [76, 8, 606]\n" +
                "  [77, 8, 77]\n" +
                "  [78, 8, 767]\n" +
                "  [10, 9, 76]\n" +
                "  [11, 9, 983]\n" +
                "  [12, 9, 410]\n" +
                "  [13, 9, 645]\n" +
                "  [14, 9, 363]";

        String[] ss = input.split("\n");
        int[][] B = new int[ss.length][];

        for (int i = 0; i < ss.length; i++) {
            String s = ss[i]
                    .replace("[", "")
                    .replace("]", "")
                    .replace(" ", "");
            String[] edge = s.split(",");
            int[] B_i = new int[edge.length];
            for (int j = 0; j < edge.length; j++) {
                B_i[j] = Integer.valueOf(edge[j]);
            }
            B[i] = B_i;
        }

        instance.solve2(78, B); // = 7399
    }
}
