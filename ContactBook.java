// Author Kresimir Tuk
// OOP assignment 2. PhoneBook in Java Swing

import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class ContactBook {

        public static void main(String[]args){

            JFrame frame = new JFrame();
            JTable table = new JTable();

            // table headers setting table model
            Object[] columns = {"Name", "LastName", "PhNumber"}; // array of headers names
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columns);// headers
            table.setModel(model);
            // table row sorter wraping our table 'model'
            TableRowSorter<TableModel> tr = new TableRowSorter<TableModel>(model);
            table.setRowSorter(tr);


            //text fields for user input
            JTextField textName = new JTextField("");
            JTextField textLastName = new JTextField("");
            JTextField textPhNumber = new JTextField("");
            JTextField textSearch = new JTextField("");

            // to instantiate buttons
            JButton buttonLoad = new JButton("Load");
            JButton buttonSave = new JButton("Save");
            JButton buttonADD = new JButton("Add");
            JButton buttonDelete = new JButton("Delete");

            // declaring labels for text fields
            JLabel searchLabel = new JLabel("Search:");
            JLabel nameLabel = new JLabel("Name:");
            JLabel lastNameLabel = new JLabel("lastName:");
            JLabel PhNumberLabel = new JLabel("PhNumber:");

            //position of text fields
            textName.setBounds(150,220,100,25);
            textLastName.setBounds(150,265,100,25);
            textPhNumber.setBounds(150,310,100,25);
            textSearch.setBounds(510,265,300,25);

            // poistion of buttons
            buttonLoad.setBounds(640,220,100,25);
            buttonSave.setBounds(410,220,100,25);
            buttonADD.setBounds(280,220,100,25);
            searchLabel.setBounds(440,265,100,25);
            buttonDelete.setBounds(280,310,100,25);
            // poistion of labels
            nameLabel.setBounds(20,220,100,25);
            lastNameLabel.setBounds(20,265,100,25);
            PhNumberLabel.setBounds(20,310,100,25);

            // poistion of table and wraping on new pane
            JScrollPane pane = new JScrollPane(table);
            pane.setBounds(0,0,880,200);


            //add everything on frame
            frame.setLayout(null);

            frame.add(pane);

            frame.add(nameLabel);
            frame.add(lastNameLabel);
            frame.add(PhNumberLabel);

            frame.add(textName);
            frame.add(textLastName);
            frame.add(textPhNumber);
            frame.add(textSearch);

            frame.add(buttonLoad);
            frame.add(buttonSave);
            frame.add(buttonADD);
            frame.add(searchLabel);
            frame.add(buttonDelete);

            // each index representing column
            Object[] row = new Object[3];

            buttonADD.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    //get text from text fields and add them to table
                    row[0] = textName.getText();
                    row[1] = textLastName.getText();
                    row[2] = textPhNumber.getText();

                    model.addRow(row);
                }
            });
            buttonDelete.addActionListener(new ActionListener() {
                @Override
                //remove selected row
                public void actionPerformed(ActionEvent actionEvent) {
                    int i = table.getSelectedRow();
                    if(i >= 0 ){
                        model.removeRow(i);
                    }
                    else{
                        System.out.println("Delete error");
                    }
                }
            });

            table.addMouseListener(new MouseAdapter() {
                @Override
                // mouse listener to get selected row on click and return data as string
                public void mouseClicked(MouseEvent e) {
                    //double click selected row and update it
                    int i = table.getSelectedRow();
                    textName.setText(model.getValueAt(i,0).toString());
                    textLastName.setText(model.getValueAt(i,1).toString());
                    textPhNumber.setText(model.getValueAt(i,2).toString());
                }
            });

            textSearch.addKeyListener(new KeyListener() {
                @Override
                //filter on key events with 'Row Filter' method which is provided by JTable library'
                public void keyTyped(KeyEvent keyEvent) {
                    String query = textSearch.getText().toLowerCase();
                    tr.setRowFilter(RowFilter.regexFilter(query));

                }

                @Override
                public void keyPressed(KeyEvent keyEvent) {
                    String query = textSearch.getText().toLowerCase();
                    tr.setRowFilter(RowFilter.regexFilter(query));

                }

                @Override
                public void keyReleased(KeyEvent keyEvent) {
                    String query = textSearch.getText().toLowerCase();
                    tr.setRowFilter(RowFilter.regexFilter(query));

                }
            });


            buttonSave.addActionListener(new ActionListener() {
                @Override
                // function to save file with JFileChooser
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        JFileChooser jF = new JFileChooser(new File("C://"));
                        jF.setDialogTitle("Save a file");
                        int result = jF.showSaveDialog(null);
                        if (result == JFileChooser.APPROVE_OPTION)
                                System.out.println("File: "+ jF.getSelectedFile());
                            FileOutputStream  out;
                            PrintStream pS;
                            out = new FileOutputStream(jF.getSelectedFile());
                            pS = new PrintStream(out);
                            // Print to file
                        for (int i = 0;i < table.getRowCount();i ++) {
                            for (int j = 0; j < table.getColumnCount(); j++) {
                                pS.print(table.getValueAt(i, j).toString()+" ");//separate by space
                            }
                            pS.println(" ");//than again, but after each row

                        }
                        pS.close();
                            JOptionPane.showMessageDialog(null, " Saved!");

                    }catch(Exception e){ System.out.println(e);}
                }
            });
            buttonLoad.addActionListener(new ActionListener() {
                @Override
                //load file with Jfilechooser
                public void actionPerformed(ActionEvent actionEvent) {
                    JFileChooser fc = new JFileChooser(new File( "C://"));
                    int returnVal = fc.showOpenDialog(null);
                    if(returnVal == JFileChooser.APPROVE_OPTION)
                        System.out.println("File: " + fc.getSelectedFile());
                    try{
                        //bufferd reader 'in' to array line by line
                        FileReader fr = new FileReader(fc.getSelectedFile());
                        BufferedReader in = new BufferedReader(fr);

                        Object[] inLine = in.lines().toArray();
                        // output file as string and split it by spaces
                        for (int i = 0; i < inLine.length; i++){
                            String[] row1 = inLine[i].toString().split(" ");
                            model.addRow(row1);
                            }
                            in.close();
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }
            });


            frame.setSize(900,400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
}
