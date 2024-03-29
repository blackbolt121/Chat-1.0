/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.tecnm.jdbc.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author rgo19
 */
public class Cliente extends javax.swing.JFrame {

    /**
     * Creates new form Server
     */
    public Cliente() {
        this.setTitle("Cliente");
        initComponents();
        initComponents2();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void initComponents2() {
        jTextField1.setText("");
        jTextArea1.setEditable(false);
        try {
            ts = new TextClient(jButton1, jTextArea1);
            Thread thread = new Thread(ts);
            thread.start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (!(jTextField1.getText().length() == 0 || jTextField1.getText().matches("^\\s+$") || ts == null)) { //Ley de Morgan
            ts.setMensaje(jTextField1.getText());
            jTextField1.setText("");
        } else {
            if (jTextField1.getText().matches("^\\s+$")) {
                jTextField1.setText("");
            }
            if (ts == null) {
                jButton1.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        Cliente cl = new Cliente();
        cl.setVisible(true);
        /* Create and display the form */

    }
    private TextClient ts;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}

class TextClient implements Runnable {

    private Socket cs;
    private JButton b;
    private JTextArea t;
    private String mensaje;
    public final static int PORT = 1234;

    TextClient(JButton b, JTextArea t) throws IOException {
        cs = new Socket("127.0.0.1", PORT);
        this.b = b;
        this.t = t;
        mensaje = "";

    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public void run() {
        b.setEnabled(false);
        DataOutputStream os = null;
        DataInputStream is = null;
        try {
            os = new DataOutputStream(cs.getOutputStream());
            is = new DataInputStream(cs.getInputStream());
            b.setEnabled(true);
            while (true) {
                Thread.sleep(50);
                
                if (!(mensaje.length() == 0 || mensaje.matches("^\\s+$"))) {
                    if(mensaje.equalsIgnoreCase("---quit---")){
                        Thread.sleep(100);
                        os.writeUTF(mensaje);
                        os.close();
                        is.close();
                        System.exit(0);
                    }else{
                        t.setText(t.getText() + "[Cliente]: " + mensaje + "\n");
                        os.writeUTF(mensaje);
                        mensaje = "";
                    }
                    
                    
                }
                if (is.available() > 0) 
                {
                    String var = is.readUTF();
                    
                    if(var.equals("---quit---") )
                    {
                        os.close();
                        is.close();
                        System.exit(0);
                    }else{
                        t.setText(t.getText() + "[Servidor]: " + var + "\n");
                    }
                }
            }

        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Se ha perdido la conexión con el servidor");
            b.setEnabled(false);
            System.exit(0);
        }
        b.setEnabled(false);
        try {
            if (!(os == null || is == null)) {
                os.close();
                is.close();
            }

        } catch (IOException ex) {
            //Logger.getLogger(TextClient.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Se ha perdido la conexión con el servidor");
            b.setEnabled(false);
        }
    }

}
