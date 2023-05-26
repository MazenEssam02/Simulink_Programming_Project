package com.example.app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Taking File Path From The User
        /*
        System.out.println("Please Enter Simulink MDL File Path:");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        System.out.println(filePath);
        scanner.close();
        */

        // Reading File
        BufferedReader reader = new BufferedReader(new FileReader("src/main/Example.mdl"));
        String lineAcc = "";
        StringBuilder fileContent = new StringBuilder();
        while ((lineAcc = reader.readLine()) != null) {
            fileContent.append(lineAcc);
            fileContent.append("\n");
        }
        reader.close();

        // Extract System Content
        String[] systemFileContent = regexMatcher("<(\\bSystem\\b)>.*</\\bSystem\\b>", String.valueOf(fileContent));

        // Creating Blocks Strings
        String[] blocksContent = regexMatcher("<\\bBlock\\b[^>]*>.*?</\\bBlock\\b>", systemFileContent[0]);

        // Creating Lines Array
        Block[] blocks = getBlocks(blocksContent);

        // Creating Lines Strings
        String[] linesContent = regexMatcher("<\\bLine\\b[^>]*>.*?</\\bLine\\b>", systemFileContent[0]);

        // Creating Lines Array
        Line[] lines = getLines(linesContent);



        Pane pane = new Pane();
        for(int i=0;i<blocks.length;i++) {
            Rectangle rec1 = new Rectangle();
            Rectangle rec2 = new Rectangle();
            Text add = new Text(blocks[i].getName());
            add.setX(blocks[i].getPosition()[0] + rec1.getWidth()/4);
            add.setY(blocks[i].getPosition()[1] +50);
            add.setFill(Color.BLUE);
            add.setFont(Font.font("Arial", FontWeight.BOLD,10));
            rec1.setX(blocks[i].getPosition()[0]);
            rec1.setY(blocks[i].getPosition()[1]);
            rec2.setX(blocks[i].getPosition()[0]);
            rec2.setY(blocks[i].getPosition()[1]);
            rec1.setHeight(32);
            rec1.setWidth(30);
            rec2.setHeight(34);
            rec2.setWidth(32);
            rec1.setStroke(Color.BLACK);
            rec1.setFill(Color.WHITE);
            rec2.setFill(Color.BLUE);
            pane.getChildren().addAll(rec2,rec1,  add);
        }
        for(int j=0;j<lines.length;j++){
            javafx.scene.shape.Line line=new javafx.scene.shape.Line();
            javafx.scene.shape.Line line2=new javafx.scene.shape.Line();
            Arrow branch=new Arrow();
            Arrow branch2=new Arrow();
            Arrow line1 = new Arrow();

            int src=(lines[j].getSrc().charAt(0));
            int dst=0;
            int dst1=0;

           if(lines[j].getBranches()==null){
               dst=lines[j].getDst().charAt(0);
           }
           else{
               dst=(lines[j].getBranches()[1].getDst().charAt(0));
               dst1=(lines[j].getBranches()[0].getDst().charAt(0));
           }

            for(int x=0; x<blocks.length;x++) {
                if (dst1 == 0) {
                    if ((int) (blocks[x].getSID().charAt(0)) == src && (blocks[x].getMirror().equals("off"))) {
                        line1.setStartX(blocks[x].getPosition()[0] + 30);
                        line1.setStartY(blocks[x].getPosition()[1] + 15);

                    }
                    else if ((int) (blocks[x].getSID().charAt(0)) == src && (blocks[x].getMirror().equals("on"))) {
                        double xm = blocks[x].getPosition()[0];
                        line.setStartX(blocks[x].getPosition()[0]);
                        line.setStartY(blocks[x].getPosition()[1] + 15);
                        for (int q = 0; q < blocks.length; q++) {
                            if ((int) (blocks[q].getSID().charAt(0)) == dst && lines[j].getPoints() != null) {
                                line.setEndX(line.getStartX() + lines[j].getPoints()[0]);
                                line.setEndY(line.getStartY() + lines[j].getPoints()[1]);
                                line2.setStartX(line.getEndX());
                                line2.setStartY(line.getEndY());
                                line2.setEndX(line2.getStartX() + lines[j].getPoints()[2]);
                                line2.setEndY(line2.getStartY() + lines[j].getPoints()[3]);
                                line1.setStartX(line2.getEndX());
                                line1.setStartY(line2.getEndY());
                                line1.setEndX(blocks[q].getPosition()[0]);
                                line1.setEndY(line2.getEndY());
                            }
                        }
                    }


                    //if ((int) (blocks[x].getSID().charAt(0)) == dst && lines[j].getPoints() == null)
                    if ((int) (blocks[x].getSID().charAt(0)) == dst ) {
                        line1.setEndX(blocks[x].getPosition()[0]);
                        line1.setEndY(blocks[x].getPosition()[1] + 15);

                    }
                }
                else {
                    if ((int) (blocks[x].getSID().charAt(0)) == src) {

                        line.setStartX(blocks[x].getPosition()[0] + 30);
                        line.setStartY(blocks[x].getPosition()[1] + 15);
                        line.setEndX(line.getStartX() + lines[j].getPoints()[0]);
                        line.setEndY(line.getStartY() + lines[j].getPoints()[1]);
                        for (int m = 0; m < blocks.length; m++) {

                            if ((int) (blocks[m].getSID().charAt(0)) == dst) {
                                branch.setStartY(line.getEndY());
                                branch.setStartX(line.getEndX());
                                branch.setEndX(blocks[m].getPosition()[0]);
                                branch.setEndY(line.getEndY());

                            }
                            if (((int) (blocks[m].getSID().charAt(0)) == dst1 && (blocks[m].getMirror().equals("off")))) {
                                line2.setStartY(line.getEndY());
                                line2.setStartX(line.getEndX());
                                line2.setEndY(line.getEndY() + lines[j].getBranches()[0].getPoints()[1]);
                                line2.setEndX(line.getEndX() + lines[j].getBranches()[0].getPoints()[0]);
                                branch2.setStartX(line2.getEndX());
                                branch2.setStartY(line2.getEndY());
                                branch2.setEndX(blocks[m].getPosition()[0]);
                                branch2.setEndY(line2.getEndY());
                            }
                            else if (((int) (blocks[m].getSID().charAt(0)) == dst1) && (blocks[m].getMirror().equals("on"))) {
                                line2.setStartY(line.getEndY());
                                line2.setStartX(line.getEndX());
                                line2.setEndY(line.getEndY() + lines[j].getBranches()[0].getPoints()[1]);
                                line2.setEndX(line.getEndX() + lines[j].getBranches()[0].getPoints()[0]);
                                branch2.setStartX(line2.getEndX());
                                branch2.setStartY(line2.getEndY());
                                branch2.setEndX(blocks[m].getPosition()[0] + 30);
                                branch2.setEndY(line2.getEndY());
                            }


                        }
                    }


                }
            }
           pane.getChildren().addAll(line,line1,line2,branch,branch2);
        }
        Scene scene = new Scene(pane, 1200, 600);
        stage.setTitle("Simulink");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Extracts information about blocks from the given array of block strings.
     *
     * @param blocksStringArr Array of block strings
     * @return Array of Block objects
     */
    private static Block[] getBlocks(String[] blocksStringArr) {
        // Creating Blocks Objects
        Block[] blocks = new Block[0];

        for (String block : blocksStringArr) {
            // Extracting block information using regex
            String[] blockType = regexMatcher("(?<=BlockType=\").+?(?=\")", block);
            String[] blockName = regexMatcher("(?<=Name=\").+?(?=\")", block);
            String[] blockSID = regexMatcher("(?<=SID=\").+?(?=\")", block);
            String[] blockPorts = regexMatcher("(?<=<P Name=\"Ports\">).+?(?=</P>)", block);
            String[] blockPosition = regexMatcher("(?<=<P Name=\"Position\">).+?(?=</P>)", block);
            int[] blockPositionArr = stringToIntArr(blockPosition[0]);
            String[] blockMirror = regexMatcher("(?<=<P Name=\"BlockMirror\">).+?(?=</P>)", block);

            if (blockMirror.length!=0) {

                blocks = addToArray(blocks, new Block(blockType[0], blockName[0], blockSID[0], blockPositionArr,blockMirror[0]));
            } else {
                //int[] blockPortsArr = stringToIntArr(blockPorts[0]);
                // Create block with shape and inputs
                blocks = addToArray(blocks, new Block(blockType[0], blockName[0], blockSID[0],  blockPositionArr, "off"));
            }
        }
        return blocks;
    }

    /**
     * Extracts information about lines from the given array of line strings.
     *
     * @param linesStringArr Array of line strings
     * @return Array of Line objects
     */
    private static Line[] getLines(String[] linesStringArr) {
        Line[] lines = new Line[0];

        for (String lineStr : linesStringArr) {
            // Extracting line information using regex
            String[] src = regexMatcher("(?<=<P Name=\"Src\">).*?(?=</P>)", lineStr);
            String[] dst;
            String[] points = regexMatcher("(?<=<P Name=\"Points\">).*?(?=</P>)", lineStr);
            Branch[] branches = getBranches(lineStr);
            int[] pointsArr;

            if (points.length == 0) {
                pointsArr = null;
            } else {
                pointsArr = stringToIntArr(points[0]);
            }

            if (!hasBranch(lineStr)) {
                dst = regexMatcher("(?<=<P Name=\"Dst\">)[^<]*?(?=</P>)", lineStr);
                lines = addToArray(lines, new Line(src[0], dst[0], pointsArr, branches));
                continue;
            }

            // SET LINES
            lines = addToArray(lines, new Line(src[0], pointsArr, branches));
        }
        return lines;
    }

    /**
     * Extracts information about branches from the given branches string.
     *
     * @param branchesString String containing branch information
     * @return Array of Branch objects
     */
    private static Branch[] getBranches(String branchesString) {
        Branch[] branches = new Branch[0];
        String[] branchesStringArr = { branchesString };

        String[] branchesContent = regexMatcher("<\\bBranch\\b[^>]*>.*?</\\bBranch\\b>", branchesStringArr[0]);

        if (branchesContent.length == 0) return null;

        for (String branch : branchesContent) {
            // Extracting branch information using regex
            String[] dst = regexMatcher("(?<=<P Name=\"Dst\">).*?(?=</P>)", branch);
            String[] points = regexMatcher("(?<=<P Name=\"Points\">).*?(?=</P>)", branch);
            int[] pointsArr;
            if (points.length == 0) {
                pointsArr = null;
            } else {
                pointsArr = stringToIntArr(points[0]);
            }
            branches = addToArray(branches, new Branch(dst[0], pointsArr));
        }

        return branches;
    }

    /**
     * Checks if a line has branches.
     *
     * @param lineStr Line string to check
     * @return True if the line has branches, false otherwise
     */
    private static boolean hasBranch(String lineStr) {
        String[] branchesArr = regexMatcher("<\\bBranch\\b[^>]*>.*?</\\bBranch\\b", lineStr);
        return branchesArr.length != 0;
    }

    /**
     * Matches a regex pattern against the input string and returns the matches.
     *
     * @param regexString Regex pattern string
     * @param input       Input string to match against
     * @return Array of matching strings
     */
    private static String[] regexMatcher(String regexString, String input) {
        Pattern pattern = Pattern.compile(regexString, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);

        // Create an array to store the matching results
        String[] inputs = new String[0];

        while (matcher.find()) {
            String match = matcher.group();
            inputs = addToArray(inputs, match);
        }

        return inputs;
    }

    /**
     * Adds an element to an array.
     *
     * @param array   Array to add1 the element to
     * @param element Element to add1
     * @param <T>     Type of the array and element
     * @return New array with the added element
     */
    private static <T> T[] addToArray(T[] array, T element) {
        T[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = element;
        return newArray;
    }

    /**
     * Converts a string of integers to an array of integers.
     *
     * @param string String containing integers separated by commas or semicolons
     * @return Array of integers
     */
    private static int[] stringToIntArr(String string) {
        string = string.replace("[", "").replace("]", "");
        if (string.contains(";")) {
            string = string.replace(";", ",");
        }
        String[] stringArr = string.split(",");
        int[] intArr = new int[stringArr.length];

        for (int i = 0; i < stringArr.length; i++) {
            intArr[i] = Integer.parseInt(stringArr[i].trim()); // Convert each string element to an integer
        }
        return intArr;
    }

    public static void main(String[] args) {
        launch();

    }
}
