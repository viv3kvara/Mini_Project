package My_First_Mini;

/**
 *
 * @author VIVEK
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

public class Mini_Project implements ActionListener, ItemListener, ChangeListener {

    JFrame jf;
    JTabbedPane jtp;
    JPanel pi, pr, pu, pd;
    JLabel lienr, liFirst,liLast,liSem, liBranch,luenr,luFirst, luLast,luSem,luBranch,ldenr;
    JTextField tfienr,tfiFirst,tfiLast,tfiSem,tfiBranch,tfiprice, tfuFirst,tfuLast,tfuSem,tfuBranch;
    JComboBox cbuenr, cbdenr;
    JButton bi, bu, bd;
    JTable jt;
    DefaultTableModel dtm;
    JScrollPane jsp;
    String header[];
    Connection con;
    Statement st;
    ResultSet rs;
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/student";
    String user = "root";
    String pass = "";
    String query;

    public static void main(String[] args) {
        Mini_Project miniproject = new Mini_Project();
    }

    public Mini_Project() {
        jf = new JFrame("Mini Project");
        jtp = new JTabbedPane();

        pi = new JPanel();
        pi.setLayout(null);

        // insert tab in label
        lienr = new JLabel("Enrollmnt : ");
        lienr.setBounds(20, 20, 100, 20);
        pi.add(lienr);
        liFirst = new JLabel("First Name : ");
        liFirst.setBounds(20, 70, 100, 20);
        pi.add(liFirst);
        liLast = new JLabel("Last Name : ");
        liLast.setBounds(20, 120, 100, 20);
        pi.add(liLast);
        liSem = new JLabel("Sem : ");
        liSem.setBounds(20, 160, 100, 20);
        pi.add(liSem);
        liBranch = new JLabel("Branch : ");
        liBranch.setBounds(20, 210, 100, 20);
        pi.add(liBranch);
        
        // insert tab in textfield
        tfienr = new JTextField(25);
        tfienr.setBounds(100, 20, 100, 20);
        pi.add(tfienr);
        tfiFirst = new JTextField(25);
        tfiFirst.setBounds(100, 70, 100, 20);
        pi.add(tfiFirst);
        tfiLast = new JTextField(25);
        tfiLast.setBounds(100, 120, 100, 20);
        pi.add(tfiLast);
        tfiSem = new JTextField(25);
        tfiSem.setBounds(100, 160, 100, 20);
        pi.add(tfiSem);
        tfiBranch = new JTextField(25);
        tfiBranch.setBounds(100, 210, 100, 20);
        pi.add(tfiBranch);
        
        //inset tab in button
        bi = new JButton("Insert");
        bi.setBounds(20, 250, 150, 20);
        bi.addActionListener(this);
        pi.add(bi);

      

        pu = new JPanel();
        pu.setLayout(null);
        
        //update tab in label
        luenr = new JLabel("Enrollment : ");
        luenr.setBounds(20, 20, 100, 20);
        pu.add(luenr);
        luFirst = new JLabel("First Name : ");
        luFirst.setBounds(20, 70, 100, 20);
        pu.add(luFirst);
        luLast = new JLabel("Last Name : ");
        luLast.setBounds(20, 120, 100, 20);
        pu.add(luLast);
        luSem = new JLabel("Sem : ");
        luSem.setBounds(20, 160, 100, 20);
        pu.add(luSem);
        luBranch = new JLabel("Branch : ");
        luBranch.setBounds(20, 210, 100, 20);
        pu.add(luBranch);
        //combo box for update
        cbuenr = new JComboBox();
        cbuenr.setBounds(100, 20, 120, 20);
        cbuenr.addItemListener(this);
        pu.add(cbuenr);
        
        tfuFirst = new JTextField(25);
        tfuFirst.setBounds(100, 70, 100, 20);
        pu.add(tfuFirst);
        tfuLast = new JTextField(25);
        tfuLast.setBounds(100, 120, 100, 20);
        pu.add(tfuLast);
        tfuSem = new JTextField(25);
        tfuSem.setBounds(100,160 , 100, 20);
        pu.add(tfuSem);
        tfuBranch = new JTextField(25);
        tfuBranch.setBounds(100, 210, 100, 20);
        pu.add(tfuBranch);
        
        
        bu = new JButton("Update");
        bu.setBounds(20, 250, 150, 20);
        bu.addActionListener(this);
        pu.add(bu);

        pd = new JPanel();
        pd.setLayout(null);
        //delete 
        ldenr = new JLabel("Enrollment : ");
        ldenr.setBounds(20, 20, 100, 20);
        pd.add(ldenr);
        //combo box for delete
        cbdenr = new JComboBox();
        cbdenr.setBounds(100, 20, 120, 20);
        pd.add(cbdenr);
        bd = new JButton("Delete");
        bd.setBounds(20, 120, 150, 20);
        bd.addActionListener(this);
        pd.add(bd);
        
        pr = new JPanel();
        pr.setLayout(new BorderLayout());
        disaplayRecord();

        jtp.addTab("Insert", pi);
        jtp.addTab("Retrieve", pr);
        jtp.addTab("Update", pu);
        jtp.addTab("Delete", pd);
        
        
        jtp.addChangeListener(this);
        jf.getContentPane().add(jtp);
        jf.setVisible(true);
        jf.setSize(500, 500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void disaplayRecord() {
        dtm = new DefaultTableModel();
        dtm.addColumn("Enrollment");
        dtm.addColumn("First Name");
        dtm.addColumn("Last Name");
        dtm.addColumn("Sem");
        dtm.addColumn("Branch");
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
            st = con.createStatement();
            query = "select * from user";//table name
            rs = st.executeQuery(query);
            while (rs.next()) {
                String enr = rs.getString(1);
                String fn = rs.getString(2);
                String ln = rs.getString(3);
                int sem = rs.getInt(4);
                String br = rs.getString(5);
                dtm.addRow(new Object[]{enr,fn,ln,sem,br});
            }
        } catch (Exception ex) {
            System.out.println("Exception (Display record) : " + ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ce) {
                System.out.println("Close Exception (Display record)");
            }
        }
        jt = new JTable(dtm);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jsp = new JScrollPane(jt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pr.add(jsp);
    }

    public void loadData() {
        cbuenr.removeItemListener(this);
        cbuenr.removeAllItems();
        
        cbdenr.removeAllItems();
        
        tfuFirst.setText("");
        tfuLast.setText("");
        tfuSem.setText("");
        tfuBranch.setText("");
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
            st = con.createStatement();
            query = "select enrollment from user";
            rs = st.executeQuery(query);
            while (rs.next()) {
                cbuenr.addItem(rs.getString(1));
                cbdenr.addItem(rs.getString(1));
            }
        } catch (Exception ex) {
            System.out.println("Exception (Load data): " + ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ce) {
                System.out.println("Close Exception (Load data)");
            }
            cbuenr.setSelectedIndex(-1);
            cbdenr.setSelectedIndex(-1);
            cbuenr.addItemListener(this);
        }
    }

    @Override
        public void actionPerformed(ActionEvent e) {
        String cmdbut = e.getActionCommand();
        if (cmdbut.equals("Insert") && (tfienr.getText().equals("") || tfiFirst.getText().equals("") || tfiLast.getText().equals("") || tfiSem.getText().equals("") || tfiBranch.getText().equals("") )) {
            JOptionPane.showMessageDialog(jf, "Enter details properly", "Insert Status", JOptionPane.ERROR_MESSAGE);
        } else if (cmdbut.equals("Update") && (cbuenr.getSelectedIndex() == -1 || tfuFirst.getText().equals("") || tfuLast.getText().equals("") || tfuSem.getText().equals("") || tfuBranch.getText().equals("") )) {
            JOptionPane.showMessageDialog(jf, "Enter details properly", "Update Status", JOptionPane.ERROR_MESSAGE);
        } else if (cmdbut.equals("Delete") && cbdenr.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(jf, "Enter details properly", "Delete Status", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, pass);
                st = con.createStatement();
                
                if (cmdbut.equals("Insert")) {
                    query = "insert into user values(" + tfienr.getText() + ",'" + tfiFirst.getText() + "','"+ tfiLast.getText() +  "',"+ tfiSem.getText() +  ",'"+ tfiBranch.getText() +  "')";
                    st.executeUpdate(query);
                    JOptionPane.showMessageDialog(jf, "record added successfully...", "Insert Status", JOptionPane.INFORMATION_MESSAGE);
                } else if (cmdbut.equals("Update")) {
                    query = "update user set first='" + tfuFirst.getText() + "',last='" + tfuLast.getText() + "',sem=" + tfuSem.getText() + ",branch='" + tfuBranch.getText() + "' where enrollment=" + cbuenr.getSelectedItem() + "";
                    st.executeUpdate(query);
                    JOptionPane.showMessageDialog(jf, "record updated successfully...", "Update Status", JOptionPane.INFORMATION_MESSAGE);
                } else if (cmdbut.equals("Delete")) {
                    query = "delete from user where enrollment='" + cbdenr.getSelectedItem() + "'";
                    st.executeUpdate(query);
                    JOptionPane.showMessageDialog(jf, "record deleted successfully...", "Delete Status", JOptionPane.INFORMATION_MESSAGE);
                }
                tfienr.setText("");
                tfiFirst.setText("");
                tfiLast.setText("");
                tfiSem.setText("");
                tfiBranch.setText("");
                tfuFirst.setText("");
                tfuLast.setText("");
                tfuSem.setText("");
                tfuBranch.setText("");
                cbuenr.removeItemListener(this);
                cbuenr.setSelectedIndex(-1);
                cbuenr.addItemListener(this);
                cbdenr.setSelectedIndex(-1);
            } catch (Exception ex) {
                System.out.println("Exception (CRUD): " + e);
                if (cmdbut.equals("Insert")) {
                    JOptionPane.showMessageDialog(jf, "user NOT added...", "Insert Status", JOptionPane.INFORMATION_MESSAGE);
                } else if (cmdbut.equals("Update")) {
                    JOptionPane.showMessageDialog(jf, "user NOT updated...", "Update Status", JOptionPane.INFORMATION_MESSAGE);
                } else if (cmdbut.equals("Delete")) {
                    JOptionPane.showMessageDialog(jf, "user NOT deleted...", "Delete Status", JOptionPane.INFORMATION_MESSAGE);
                }
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (st != null) {
                        st.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception ce) {
                    System.out.println("Close Exception (CRUD)");
                }
                loadData();
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
            st = con.createStatement();
            query = "select * from user where enrollment =" + cbuenr.getSelectedItem()+"";
            System.out.println(query);
            rs = st.executeQuery(query);
            rs.next();
            tfuFirst.setText(rs.getString(2) + "");
            tfuLast.setText(rs.getString(3) + "");
            tfuSem.setText(rs.getString(4) + "");
            tfuBranch.setText(rs.getString(5) + "");
        } catch (Exception ex) {
            System.out.println("Exception (Item changed): " + ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ce) {
                System.out.println("Close Exception (Item changed): ");
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int activetab = jtp.getSelectedIndex();
        if (activetab == 1) {
            pr.removeAll();
            disaplayRecord();
        }
        if (activetab == 3 || activetab == 2) {
            loadData();
        }
    }
}

