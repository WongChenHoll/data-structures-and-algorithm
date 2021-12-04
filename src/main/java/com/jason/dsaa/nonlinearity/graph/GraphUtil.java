package com.jason.dsaa.nonlinearity.graph;


import com.jason.structure.linearity.queue.LinkedQueue;

/**
 * 图的常用方法。
 * <pre>
 *     图的遍历分两种：
 *          1.广度优先搜索 breadth first search （BFS）
 *          2.深度优先搜索 depth first search （DFS）
 *     这两种搜索适用于有向图和无向图。
 * </pre>
 *
 * @author WangChenHol
 * @date 2021/6/28 14:11
 **/
public class GraphUtil {

    /**
     * 图的广度优先搜索，适用于有向图、无向图、有向网、无向网。
     *
     * @param graph 图
     * @return 返回搜索的结果
     * @throws Exception 异常
     */
    public static String breadthFirstSearch(Graph<?> graph) throws Exception {
        if (graph == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        boolean[] visited = new boolean[graph.getVertexNum()]; // 访问标志数组 默认每个元素值都是false
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        for (int i = 0; i < graph.getVertexNum(); i++) {
            if (!visited[i]) {
                visited[i] = true;
                queue.offer(i);
                builder.append(graph.getVertex(i).data).append(" ");
                while (!queue.isEmpty()) {
                    Integer poll = queue.poll();
                    for (int index = graph.firstAdjacencyVertex(poll); index >= 0; index = graph.nextAdjacencyVertex(poll, index)) {
                        if (!visited[index]) {
                            queue.offer(index);
                            visited[index] = true;
                            builder.append(graph.getVertex(index).data).append(" ");
                        }
                    }
                }
            }
        }
        return builder.toString();
    }

    /**
     * 图的深度优先搜索。
     *
     * @param graph 图
     * @return 搜索结果
     * @throws Exception 异常
     */
    public static String depthFirstSearch(Graph<?> graph) throws Exception {
        StringBuilder builder = new StringBuilder();
        boolean[] visited = new boolean[graph.getVertexNum()];

        for (int v = 0; v < graph.getVertexNum(); v++) {
            if (!visited[v]) {
                DFS(graph, visited, v, builder);
            }
        }
        return builder.toString();
    }

    private static void DFS(Graph<?> graph, boolean[] visited, int v, StringBuilder builder) throws Exception {
        visited[v] = true;
        builder.append(graph.getVertex(v).data).append(" ");
        for (int w = graph.firstAdjacencyVertex(v); w > 0; w = graph.nextAdjacencyVertex(v, w)) {
            if (!visited[w]) {
                DFS(graph, visited, w, builder);
            }
        }
    }


    /**
     * <pre>
     * 求网中某个顶点到其余顶点的最短路径。（戴克斯特拉算法）
     * 注意：此算法实现使用的邻接矩阵存储的网。
     * 该算法的时间复杂度是O(n2)
     * </pre>
     *
     * @param graph 邻接矩阵网
     * @param v0    v0顶点
     */
    public static void shortestPath_Dijkstra(AdjacencyMatrixGraph<?> graph, int v0) {
        int vertexNum = graph.getVertexNum(); // 顶点数量
        boolean[][] path = new boolean[vertexNum][vertexNum]; // v0顶点到其余顶点的最短路径，若path[v][w]==true，则w是从v0到v当前求的最短路径上的顶点。
        int[] weightLength = new int[vertexNum]; // v0到其余顶点的带权长度

        boolean[] finish = new boolean[vertexNum];
        for (int v = 0; v < vertexNum; v++) {
            finish[v] = false;
            weightLength[v] = graph.getAdjacencyMatrix()[v0][v];
            for (int w = 0; w < vertexNum; w++) {
                path[v][w] = false;
                if (weightLength[v] < Integer.MAX_VALUE) {
                    path[v][v0] = true;
                    path[v][v] = true;
                }
            }
        }
        weightLength[v0] = 0;
        finish[v0] = true;
        int v = -1;
        for (int i = 0; i < vertexNum; i++) {
            int min = Integer.MAX_VALUE;
            for (int w = 0; w < vertexNum; w++) {
                if (!finish[w]) {
                    if (weightLength[w] < min) {
                        v = w;
                        min = weightLength[w];
                    }
                }
            }
            finish[v] = true;
            for (int w = 0; w < vertexNum; w++) {
                if (!finish[w] && graph.getAdjacencyMatrix()[v][w] < Integer.MAX_VALUE && (min + graph.getAdjacencyMatrix()[v][w] < weightLength[w])) {
                    weightLength[w] = min + graph.getAdjacencyMatrix()[v][w];
                    System.arraycopy(path[v], 0, path[w], 0, path[v].length);
                    path[w][w] = true;
                }
            }
        }

        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[i].length; j++) {
                System.out.print(path[i][j] + " ");
            }
            System.out.println("");
        }
        for (int i = 0; i < weightLength.length; i++) {
            System.out.print(weightLength[i] + " ");
        }
        System.out.println("");

    }


}
