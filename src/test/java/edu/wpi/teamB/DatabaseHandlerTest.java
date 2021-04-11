package edu.wpi.teamB;

import edu.wpi.teamB.database.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseHandlerTest {
    private static String resourcesPathString;
    private static DatabaseHandler db;

    @BeforeAll
    static void initDB() {
        db = DatabaseHandler.getDatabaseHandler("test.db");
        resourcesPathString = new File("").getAbsolutePath() + "/src/test/resources/edu/wpi/teamB/database/";
    }

    @Test
    public void simpleParseNodes() {
        Node target = new Node("testNode",
                0,
                -992,
                1,
                "test_building",
                "NODETYPE",
                "Name With Many Spaces",
                "N W M S");
        Path nodes = Paths.get(resourcesPathString + "SimpleTestNodes.csv");
        Node actual = CSVHandler.loadCSVNodes(nodes).get(0);
        assertEquals(target.toString(), actual.toString());
    }

    @Test
    public void complexParseNodesLength() {
        Path nodePath = Paths.get(resourcesPathString + "ComplexTestNodes.csv");
        List<Node> nodes = CSVHandler.loadCSVNodes(nodePath);
        assertEquals(32, nodes.size());
    }

    @Test
    public void complexParseNodesValues() {
        Node target = new Node("bWALK00501", 1872, 1965, 1, "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Path nodePath = Paths.get(resourcesPathString + "ComplexTestNodes.csv");
        List<Node> nodes = CSVHandler.loadCSVNodes(nodePath);
        List<String> expanded = nodes.stream().map(Node::toString).collect(Collectors.toList());
        assertTrue(expanded.contains(target.toString()));
    }

    @Test
    public void simpleParseEdges() {
        Edge target = new Edge("bPARK00101_bWALK00101", "bPARK00101", "bWALK00101");
        Path nodes = Paths.get(resourcesPathString + "SimpleTestEdges.csv");
        Edge actual = CSVHandler.loadCSVEdges(nodes).get(0);
        assertEquals(target.toString(), actual.toString());
    }

    @Test
    public void complexParseEdgesLength() {
        Path nodePath = Paths.get(resourcesPathString + "ComplexTestEdges.csv");
        List<Edge> nodes = CSVHandler.loadCSVEdges(nodePath);
        assertEquals(31, nodes.size());
    }

    @Test
    public void complexParseEdgesValues() {
        Edge target = new Edge("bPARK01201_bWALK00501", "bPARK01201", "bWALK00501");
        Path nodePath = Paths.get(resourcesPathString + "ComplexTestEdges.csv");
        List<Edge> nodes = CSVHandler.loadCSVEdges(nodePath);
        List<String> expanded = nodes.stream().map(Edge::toString).collect(Collectors.toList());
        assertTrue(expanded.contains(target.toString()));
    }

    @Test
    void fillDatabase() {
        List<Edge> edges = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();

        //Theres a less scuffed way to do this, but hey, it works and is easy to tweak.
        Node targetNode0 = new Node("bWALK00501", 1872, 1965, 1, "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode1 = new Node("bWALK00502", 1872, 1965, 1, "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode2 = new Node("bWALK00503", 1872, 1965, 1, "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode3 = new Node("bWALK00504", 1872, 1965, 1, "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode4 = new Node("bWALK00505", 1872, 1965, 1, "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode5 = new Node("bWALK00506", 1872, 1965, 1, "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode6 = new Node("bWALK00507", 1872, 1965, 1, "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode7 = new Node("bWALK00508", 1872, 1965, 1, "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode8 = new Node("bWALK00509", 1872, 1965, 1, "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        Node targetNode9 = new Node("bWALK00510", 1872, 1965, 1, "Parking", "WALK", "Vining Street Walkway", "ViningWalk");
        nodes.add(targetNode0);
        nodes.add(targetNode1);
        nodes.add(targetNode2);
        nodes.add(targetNode3);
        nodes.add(targetNode4);
        nodes.add(targetNode5);
        nodes.add(targetNode6);
        nodes.add(targetNode7);
        nodes.add(targetNode8);
        nodes.add(targetNode9);
        Edge targetEdge = new Edge("bPARK01201_bWALK00501", "bPARK01201", "bWALK00501");
        edges.add(targetEdge);
        String query2 = "SELECT * FROM Edges";
        try {
            db.fillDatabase(edges, nodes);
            List<Node> outnodes = db.getNodeInformation();
            assert(outnodes.containsAll(nodes));
            List<Edge> outedges = db.getEdgeInformation();
            assertEquals(outedges.get(0), targetEdge);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Test
    public void testUpdateNodeCoordinates() throws SQLException {
        List<Node> actual = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        Node target = new Node("testNode",
                0,
                -992,
                1,
                "test_building",
                "NODETYPE",
                "Name With Many Spaces",
                "N W M S");
        actual.add(target);
        db.fillDatabase(edges, actual);

        String nodeID = target.getNodeID();
        int xcoord = 1;
        int ycoord = 2;
        db.updateNodeCoordinates(nodeID, xcoord, ycoord);

        String query = "SELECT xcoord FROM Nodes WHERE nodeID = '" + nodeID + "'";
        System.out.println(query);

        List<Node> nodes = db.getNodeInformation();
        assertEquals(1, nodes.get(0).getXCoord());
        assertEquals(2, nodes.get(0).getYCoord());
    }

    @Test
    public void testUpdateNodeLocationLongName() throws SQLException {
        List<Node> actual = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        Node target = new Node("testNode",
                0,
                -992,
                1,
                "test_building",
                "NODETYPE",
                "Name With Many Spaces",
                "N W M S");
        actual.add(target);
        db.fillDatabase(edges, actual);

        String nodeID = target.getNodeID();
        String longName = "test";
        db.updateNodeLocationLongName(nodeID, longName);

        List<Node> outnodes = db.getNodeInformation();
        assertEquals("test", db.getNodeInformation().get(0).getLongName());
    }
}