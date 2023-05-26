# Simulink_Programming_Project
The aim of this project is to develop a software tool that can read Simulink MDL files
and display their contents in a user-friendly way using a Java-based graphical user
interface (GUI). Simulink is a popular simulation and modeling environment used in
various industries, including automotive, aerospace, and electronics. The Simulink MDL
files contain the model information, which is used to simulate and analyze the system
behavior.
The tool will provide users with the ability to load Simulink MDL files and view their
contents in a hierarchical structure. The GUI will allow users to navigate through the
model components and see their properties and connections. The tool will also enable
users to interactively modify the model and visualize the changes in real-time.
The software will consist of two main components: a Simulink MDL file parser and a
Java-based GUI. The parser will be responsible for reading the MDL file and extracting
the model information, including the block diagram, parameters, and connections. The
GUI will provide a user-friendly interface for displaying the model and enabling user
interaction.

## Files
- `Block.java`: Is a class having construcors of Block and Getter for it's attributes like (Name,SID,BlockType,Position, Mirror).

- `Line.java` : Is a class having construcors of Line and Getter for it's attributes like(Src,Dst,Points,Branches).

- `Branch.java`: Is a class having construcors of Branch and Getter for it's attributes like(Dst,Points).

- `Arrow.java` :Is a line with an arrow by using a custom implementation of the Shape class.

- `HelloApplication.java`: Contains GUI code and The Extraction of Block,Line and Branches and return them in array of their class.

- `Screenshot_Of_Output.jpg`:A screenshot showing the output.

- `Example.mdl`: The MDL File that we extract data from. 
