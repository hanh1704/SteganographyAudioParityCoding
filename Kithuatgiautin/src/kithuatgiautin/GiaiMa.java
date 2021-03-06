/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kithuatgiautin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Integer.parseInt;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author phamm
 */
public class GiaiMa extends javax.swing.JFrame {
 public AudioStream as;
    /**
     * Creates new form GiaiMa
     */
    public GiaiMa() {
        initComponents();
        playDebt.setEnabled(false);
        stopDebt.setEnabled(false);
    }
     public void BackgroundImageJFrame() {

           setSize(400,400);
           setVisible(true);

           setLayout(new BorderLayout());

           JLabel background=new JLabel(new ImageIcon("./testk.jpg"));

           add(background);

           background.setLayout(new FlowLayout());
     }
     public String [] CovertAudioToBinGiaiMa() throws FileNotFoundException, IOException 
{
         String path=SourceAudioDecodetxt.getText();
         File file = new File(path);
        byte[] data = new byte[(int) file.length()];
        FileInputStream in = new FileInputStream(file);
        in.read(data);
        StringBuilder binary = new StringBuilder();
        for (byte b : data) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val &128) == 0 ? 0 : 1);//dao bit,
               val <<= 1;
                if (i==7)
                {
                   binary.append("-");
                }
            }
            
        }
       
        String b=binary.toString();
        String []c=b.split("-");
       
        return c;
    }
     public String [] checkParityDECODE(int PIN, String [] MangNho)
{
   String [] thongdiep=new String[PIN];
 
   for (int i=0;i<PIN;i++)
   {
      String [] l=MangNho[i].split("");
      int count=0;
       for (int j=0;j<l.length;j++)
       {
           if (l[j].equals("1")==true)
                   {
                     count++;
                   }
       }
           if (count%2==0)
           {
               thongdiep[i]="0";
           }
           if (count%2!=0)
           {
                thongdiep[i]="1";
           }
    }
    
       String [] z= new String[PIN/8];
       for (int j=0;j<z.length;j++)
       {
                z[j]="";
            for (int i=j*8;i<(j+1)*8;i++)
            {
                z[j]+=thongdiep[i];
            }
       }
       

    return z;
}
public String chuyendoi (String [] messageBianry)
{

    byte [] b=new byte[messageBianry.length];
   
            for (int j=0;j<messageBianry.length;j++)
            { 
                 b[j] =(byte) Integer.parseInt(messageBianry[j], 2);
            }
           String s=new String(b,StandardCharsets.UTF_8);
           return s;
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        SourceAudioDecodetxt = new javax.swing.JTextField();
        ChooseAudioDecode = new javax.swing.JButton();
        playDebt = new javax.swing.JButton();
        stopDebt = new javax.swing.JButton();
        PIN = new javax.swing.JTextField();
        GiaiMaAudiobt = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Plantexttxt = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SourceAudioDecodetxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SourceAudioDecodetxtActionPerformed(evt);
            }
        });
        jPanel2.add(SourceAudioDecodetxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 290, -1));

        ChooseAudioDecode.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChooseAudioDecode.setText("Choose");
        ChooseAudioDecode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseAudioDecodeActionPerformed(evt);
            }
        });
        jPanel2.add(ChooseAudioDecode, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, -1, -1));

        playDebt.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        playDebt.setText("Play");
        playDebt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playDebtActionPerformed(evt);
            }
        });
        jPanel2.add(playDebt, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, 70, -1));

        stopDebt.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        stopDebt.setText("Stop");
        stopDebt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopDebtActionPerformed(evt);
            }
        });
        jPanel2.add(stopDebt, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 150, 60, -1));
        jPanel2.add(PIN, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 200, 100, -1));

        GiaiMaAudiobt.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        GiaiMaAudiobt.setText("Decrypt");
        GiaiMaAudiobt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GiaiMaAudiobtActionPerformed(evt);
            }
        });
        jPanel2.add(GiaiMaAudiobt, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 200, -1, 30));

        Plantexttxt.setColumns(20);
        Plantexttxt.setRows(5);
        jScrollPane1.setViewportView(Plantexttxt);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, 290, 81));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Source");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 60, 30));

        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Message");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("KEY");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, -1, -1));

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DECRYPT");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, 40));

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton5.setText("Close");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 310, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/test.jpg"))); // NOI18N
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1, 800, 350));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SourceAudioDecodetxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SourceAudioDecodetxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SourceAudioDecodetxtActionPerformed

    private void GiaiMaAudiobtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GiaiMaAudiobtActionPerformed
        ParityCoding pr=new ParityCoding();
        String pin=PIN.getText();
      int p=Integer.parseInt(pin);
          try {
          String s[]=CovertAudioToBinGiaiMa();
          String [] MangAudio= pr.MangSau(s);
          String [] mangnho= pr.TachAudioThanhNhieuMau(p, MangAudio);
          String []t=  checkParityDECODE(p, mangnho);
          String text=chuyendoi(t);
          Plantexttxt.setText(text);
      
        } catch (IOException ex) {
            Logger.getLogger(ParityCoding.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_GiaiMaAudiobtActionPerformed

    private void ChooseAudioDecodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChooseAudioDecodeActionPerformed
      JFileChooser fc = new JFileChooser();//doi tuong de chon file tu window
        FileNameExtensionFilter filterExten = new FileNameExtensionFilter("only use wav", "wav");
        fc.setFileFilter(filterExten);
        fc.showDialog(ChooseAudioDecode, "FileUpLoad");
        String path = SourceAudioDecodetxt.getText() + fc.getSelectedFile();//lay duong dan cua filed SourceFileAudio(pt getSelectedFile cua doi tuong fc)
        SourceAudioDecodetxt.setText(path);

        if (SourceAudioDecodetxt.getText().equals(path) && !path.equals("null")) {
            playDebt.setEnabled(true);
            stopDebt.setEnabled(true);
        }
    }//GEN-LAST:event_ChooseAudioDecodeActionPerformed

    private void playDebtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playDebtActionPerformed
            InputStream in;
        try {
            in = new FileInputStream(SourceAudioDecodetxt.getText());
            as = new AudioStream(in);
            AudioPlayer.player.start(as);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParityCoding.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ParityCoding.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_playDebtActionPerformed

    private void stopDebtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopDebtActionPerformed
         if (as != null) {
            AudioPlayer.player.stop(as);
        }

    }//GEN-LAST:event_stopDebtActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
     super.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(GiaiMa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GiaiMa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GiaiMa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GiaiMa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GiaiMa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ChooseAudioDecode;
    private javax.swing.JButton GiaiMaAudiobt;
    private javax.swing.JTextField PIN;
    private javax.swing.JTextArea Plantexttxt;
    private javax.swing.JTextField SourceAudioDecodetxt;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton playDebt;
    private javax.swing.JButton stopDebt;
    // End of variables declaration//GEN-END:variables
}
