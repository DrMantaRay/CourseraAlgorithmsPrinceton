import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private ArrayList<Point2D> rectList;
    private double distance;
    private Point2D nearestPoint;
    private int size;
    private Node root;
    private class Node {
        Point2D point;
        Node left;
        Node right;
        boolean color;
        private  Node (Point2D p, boolean c) {
            point = p;
            left = null;
            right = null;
            color = c;
        }
    }
    public         KdTree() {
        rectList = new ArrayList<>();
        size = 0;
        distance = -1;
        root = null;
        nearestPoint = null;
    }                               // construct an empty set of points
    public           boolean isEmpty() {return size == 0;}                     // is the set empty?
    public               int size()         { return size; }                 // number of points in the set
    public              void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            root = new Node(p, true);
            size++;
        }
        else if (contains(p)) {
            return;
        }
        else {
            Node cur_node = root;
            while (true) {
                if (cur_node.color) {
                    if (cur_node.point.x() > p.x()) {
                        if (cur_node.left == null) {
                            cur_node.left = new Node(p, !cur_node.color);
                            size++;
                            break;
                        } else {
                            cur_node = cur_node.left;
                        }
                    } else {
                        if (cur_node.right == null) {
                            cur_node.right = new Node(p, !cur_node.color);
                            size++;
                            break;
                        } else {
                            cur_node = cur_node.right;
                        }
                    }
                } else {
                    if (cur_node.point.y() > p.y()) {
                        if (cur_node.left == null) {
                            cur_node.left = new Node(p, !cur_node.color);
                            size++;
                            break;
                        } else {
                            cur_node = cur_node.left;
                        }
                    } else {
                        if (cur_node.right == null) {
                            cur_node.right = new Node(p, !cur_node.color);
                            size++;
                            break;
                        } else {
                            cur_node = cur_node.right;
                        }
                    }
                }
            }
        }
    }
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        Node cur_node = root;
        while (true) {
            if (cur_node == null) {
                return false;
            }
            if (cur_node.point.equals(p)) {
                return true;
            }
            if (cur_node.color) {
                if (cur_node.point.x() > p.x()) {
                    cur_node = cur_node.left;
                }
                else {
                    cur_node = cur_node.right;
                }
            }
            else {
                if (cur_node.point.y() > p.y()) {
                    cur_node = cur_node.left;
                }
                else {
                    cur_node = cur_node.right;
                }
            }
        }
    }
    public              void draw() {
        drawIter(root, 0 , 0, 1 , 1);
    }
    private void drawIter(Node n, double minx, double miny, double maxx, double maxy) {
        if (n != null){
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(n.point.x(), n.point.y());
            StdDraw.setPenRadius(0.005);
            if (n.color == true) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(n.point.x(), miny, n.point.x(), maxy);
                drawIter(n.left, minx, miny, n.point.x(), maxy );
                drawIter(n.right, n.point.x(), miny, maxx, maxy);
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(minx, n.point.y(), maxx, n.point.y());
                drawIter(n.left, minx, miny, maxx, n.point.y());
                drawIter(n.right, minx, n.point.y(), maxx, maxy);
            }



        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }
        rectList.clear();
        rangeIter(rect, root, new RectHV(0, 0, 1, 1));
        return  rectList;
    }
    private void rangeIter(RectHV rect, Node n, RectHV boundary) {
        if (n == null) {
            return;
        }
        if (rect.contains(n.point)) {
            rectList.add(n.point);
        }

        if (rect.intersects(boundary)) {
            if (n.color == true) {
                rangeIter(rect, n.right, new RectHV(n.point.x(), boundary.ymin(), boundary.xmax(), boundary.ymax()));
                rangeIter(rect, n.left, new RectHV(boundary.xmin(), boundary.ymin(), n.point.x(), boundary.ymax()));
            }
            else if (n.color == false){
                rangeIter(rect, n.right, new RectHV(boundary.xmin(), n.point.y(), boundary.xmax(), boundary.ymax()));
                rangeIter(rect, n.left, new RectHV(boundary.xmin(), boundary.ymin(), boundary.xmax(), n.point.y()));
            }
        }
    }
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        distance = -1;
        nearestIter(p, root, new RectHV(0, 0, 1, 1));
        return nearestPoint;
    }
    private void nearestIter(Point2D p, Node n, RectHV boundary) {
        if (n == null) {
            return;
        }
        if (distance == -1) {
            nearestPoint = n.point;
            distance = n.point.distanceTo(p);
            if (n.color == true) {
                nearestIter(p, n.right, new RectHV(n.point.x(), boundary.ymin(), boundary.xmax(), boundary.ymax()));
                nearestIter(p, n.left, new RectHV(boundary.xmin(), boundary.ymin(), n.point.x(), boundary.ymax()));
            }
            else if (n.color == false){
                nearestIter(p, n.right, new RectHV(boundary.xmin(), n.point.y(), boundary.xmax(), boundary.ymax()));
                nearestIter(p, n.left, new RectHV(boundary.xmin(), boundary.ymin(), boundary.xmax(), n.point.y()));
            }
        }
        else if (distance > boundary.distanceTo(p)) {
            if (distance > n.point.distanceTo(p)) {
                distance = n.point.distanceTo(p);
                nearestPoint = n.point;
            }
            if (n.color == true) {
                RectHV rightRect = new RectHV(n.point.x(), boundary.ymin(), boundary.xmax(), boundary.ymax());
                RectHV leftRect = new RectHV(boundary.xmin(), boundary.ymin(), n.point.x(), boundary.ymax());
                if (rightRect.distanceTo(p) < leftRect.distanceTo(p)) {
                    nearestIter(p, n.right, rightRect);
                    nearestIter(p, n.left, leftRect);
                }
                else {
                    nearestIter(p, n.left, leftRect);
                    nearestIter(p, n.right, rightRect);
                }
            }
            else if (n.color == false){
                RectHV leftRect = new RectHV(boundary.xmin(), boundary.ymin(), boundary.xmax(), n.point.y());
                RectHV rightRect = new RectHV(boundary.xmin(), n.point.y(), boundary.xmax(), boundary.ymax());
                if (rightRect.distanceTo(p) < leftRect.distanceTo(p)) {
                    nearestIter(p, n.right, rightRect);
                    nearestIter(p, n.left, leftRect);
                }
                else {
                    nearestIter(p, n.left, leftRect);
                    nearestIter(p, n.right, rightRect);

                }
            }
        }
    }


    public static void main(String[] args){

    }
}