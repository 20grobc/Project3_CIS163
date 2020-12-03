package Project3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

/*****************************************************************
 *
 *  Video Game Reservation System
 *
 *****************************************************************/
public class GUIRentalStore extends JFrame implements ActionListener {
    /** Holds menu bar */
    private JMenuBar menus;

    /** menus in the menu bar */
    private JMenu fileMenu;
    private JMenu actionMenu;

    /** menu items in each of the menus */
    private JMenuItem openSerItem;
    private JMenuItem exitItem;
    private JMenuItem saveSerItem;

    private JMenuItem reserveConsoleItem;
    private JMenuItem reserveGameItem;
    private JMenuItem returnedOutItem;

    private JMenuItem currentRentedScn;
    private JMenuItem rentedOutItemScn;


    private JPanel panel;

    /** Holds the list engine */
    private ListModel DList;

    /** Holds jTable */
    private JTable jTable;

    /** Scroll pane */
    private JScrollPane scrollList;

    /*****************************************************************
     *
     * A constructor that starts a new GUI1024 for the rental store
     *
     *****************************************************************/
    public GUIRentalStore(){
        //adding menu bar and menu items
        menus = new JMenuBar();
        fileMenu = new JMenu("File");
        actionMenu = new JMenu("Action");
        openSerItem = new JMenuItem("Open File");
        exitItem = new JMenuItem("Exit");
        saveSerItem = new JMenuItem("Save File");

        reserveConsoleItem = new JMenuItem("Reserve a Console");
        reserveGameItem = new JMenuItem("Reserve a Game");
        returnedOutItem = new JMenuItem("Return Console or Game");

        currentRentedScn = new JMenuItem("Current Store log");
        rentedOutItemScn = new JMenuItem("Rented out screen");

        //adding items to bar
        fileMenu.add(openSerItem);
        fileMenu.add(saveSerItem);
        fileMenu.addSeparator();
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        fileMenu.addSeparator();
        fileMenu.add(currentRentedScn);
        fileMenu.add(rentedOutItemScn);

        actionMenu.add(reserveConsoleItem);
        actionMenu.add(reserveGameItem);
        actionMenu.addSeparator();
        actionMenu.add(returnedOutItem);

        menus.add(fileMenu);
        menus.add(actionMenu);

        //adding actionListener
        openSerItem.addActionListener(this);
        saveSerItem.addActionListener(this);
        exitItem.addActionListener(this);
        reserveConsoleItem.addActionListener(this);
        reserveGameItem.addActionListener(this);
        returnedOutItem.addActionListener(this);

        currentRentedScn.addActionListener(this);
        rentedOutItemScn.addActionListener(this);

        setJMenuBar(menus);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        DList = new ListModel();
        jTable = new JTable(DList);
        scrollList = new JScrollPane(jTable);
        panel.add(scrollList);
        add(panel);
        scrollList.setPreferredSize(new Dimension(800,800));

        setVisible(true);
        setSize(950,850);
    }

    public void actionPerformed(ActionEvent e) {
        Object comp = e.getSource();
        returnedOutItem.setEnabled(true);

        if (currentRentedScn == comp)
            DList.setDisplay(ScreenDisplay.CurrentRentalStatus);

        if (rentedOutItemScn == comp) {
            DList.setDisplay(ScreenDisplay.RetendItems);
            returnedOutItem.setEnabled(false);
        }

        if (openSerItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (openSerItem == comp)
                    DList.loadDatabase(filename);
            }
        }

        if (saveSerItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showSaveDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (saveSerItem == e.getSource())
                    DList.saveDatabase(filename);
            }
        }

        //MenuBar options
        if(e.getSource() == exitItem){
            System.exit(1);
        }
        if(e.getSource() == reserveConsoleItem){
            Console newConsole = new Console();
            RentConsoleDialog dialog = new RentConsoleDialog(this, newConsole);
            if(dialog.getCloseStatus() == RentConsoleDialog.OK){
                DList.add(newConsole);
            }
        }
        if(e.getSource() == reserveGameItem){
            Game newGame = new Game();
            RentGameDialog dialog = new RentGameDialog(this, newGame);
            if(dialog.getCloseStatus() == RentGameDialog.OK){
                DList.add(newGame);
            }
        }

        if (returnedOutItem == e.getSource()) {
            int index = jTable.getSelectedRow();
            if (index != -1) {
                GregorianCalendar dat = new GregorianCalendar();

                Rental unit = DList.get(index);
                ReturnedOnDialog dialog = new ReturnedOnDialog(this, unit);

                JOptionPane.showMessageDialog(null,
                        "  Be sure to thank " + unit.getNameOfRenter() +
                        "\n for renting with us and the price is:  " +
                        unit.getCost(unit.getActualDateReturned()) +
                        " dollars");
                DList.upDate(index, unit);
            }
        }
    }

    public static void main(String[] args) {
        new GUIRentalStore();
    }
}
