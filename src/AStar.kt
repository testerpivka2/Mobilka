import java.util.PriorityQueue
import kotlin.math.abs

data class Point(val x: Int, val y: Int)

data class Node(val point: Point, val g: Int, val f: Int):
        Comparable<Node>{ override fun compareTo(other: Node):
        Int = this.f.compareTo(other.f)}

fun heuristic(firstPoint: Point, secondPoint: Point): Int{
    return abs(firstPoint.x-secondPoint.x) +(firstPoint.y-secondPoint.y)
}

fun AStar(matrixMap: Array<IntArray>, startPos: Point, endPos: Point): List<Point>?{
    val rows = matrixMap.size
    val cols = matrixMap[0].size

    val queue = PriorityQueue<Node>()
    val visitedPoint = HashSet<Point>()
    val parents = HashMap<Point,Point>()
    val bestG = HashMap<Point, Int>()

    queue.add(Node(startPos,0,heuristic(startPos,endPos)))

    bestG[startPos]=0;

    while(queue.isNotEmpty()){
        val current = queue.poll()!!.point

        if (current==endPos){
            return path(parents,current)
        }

        val neighbors = listOf(
            Point(current.x+1,current.y),
            Point(current.x,current.y+1),
            Point(current.x-1,current.y),
            Point(current.x,current.y-1)
        )

        for (neighbor in neighbors) {
            if (neighbor.x !in 0 until rows || neighbor.y !in 0 until cols ||
                matrixMap[neighbor.x][neighbor.y] == 0 || neighbor in visitedPoint)
                continue
            val currentCostTheStart = bestG[current]!! + matrixMap[neighbor.x][neighbor.y]

            if (currentCostTheStart<(bestG[neighbor] ?: Int.MAX_VALUE)){
                parents[neighbor] = current
                bestG[neighbor] = currentCostTheStart
                val bestF = currentCostTheStart + heuristic(neighbor,endPos)

                if (queue.none() {it.point==neighbor}) {
                    queue.add(Node(neighbor,currentCostTheStart,bestF))
                }
            }
        }
    }
    return null
}

fun path(parents: Map<Point,Point>, last: Point): List<Point>{
    val path = mutableListOf<Point>()
    var current: Point? = last
    while(current!=null){
        path.add(0,current)
        current = parents[current]
    }
    return path
}