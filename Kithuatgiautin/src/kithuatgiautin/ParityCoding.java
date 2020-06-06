/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kithuatgiautin;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import sun.audio.AudioPlayer;
import static sun.audio.AudioPlayer.player;
import sun.audio.AudioStream;
import static sun.security.krb5.Confounder.bytes;
//import javax.sound.sampled.AudioFileFormat;

/**
 *
 * @author phamm
 */
public class ParityCoding extends javax.swing.JFrame {

    public AudioStream as;
    StringBuilder NoiMangText;
    byte[] byteSau;
    int [] b;
   
    public ParityCoding() {
    
        initComponents();
        PlayAudiobt.setEnabled(false);
        Pausebt.setEnabled(false);
        

    }

    public StringBuilder CovertTextToBinary(String c) 
    {
        int PIN=c.length()*8;
        keytxt.setText(Integer.toString(PIN));
       StringBuilder NoiManText = new StringBuilder();
       // chiều dài message c.length()
        char[] k = new char[c.length()];
        int i;
        int ascill[] = new int[c.length()];
        String binary[] = new String[c.length()];
        for (i = 0; i < c.length(); i++)
        {
            // gán từng kí tự trong message c cho mảng k
            k[i] = c.charAt(i);
            //chuyen tu kí tự hiện tại ra he 10
            ascill[i] = (int) k[i];
            //chuyen hexa qua dang bi
            binary[i] = Integer.toBinaryString(ascill[i]);
            // them vao de du 8 bit
            while (binary[i].length() < 8) 
            {
                binary[i] = '0' + binary[i];
            }

           NoiManText.append(binary[i]);
      
        }
        
        return NoiManText;

    }
    
    public String [] CovertAudioToBin() throws FileNotFoundException, IOException {
        String path=SourceFileAudio.getText();
      File file = new File(path);
        byte[] data = new byte[(int) file.length()];
        FileInputStream in = new FileInputStream(file);
        in.read(data);
        StringBuilder binary = new StringBuilder();

        String binar="";

        for (byte b : data) {
            int p=0;
          int val=b;
          for (int i=0;i<8;i++)
          {
            if ((val &128) == 0 )
                   {
                       binary.append(0);
                   }
                   if ((val &128) ==128 )
                   {
                       binary.append(1);
                   }
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

//bao ve tinh toan ven cua audio/ header
    public StringBuilder Tach44byteToNewArray(String [] AudioArrray) {
        StringBuilder gop=new StringBuilder();
        String [] MangDau=new String[44];
            System.arraycopy(AudioArrray, 0, MangDau, 0, 44);
            //gop thanh 1 day
            for (int j = 0; j < 44; j++)
            {
            gop.append(MangDau[j]);
            }
        return gop;
           }
    
//lay data giau tin
    public String [] MangSau(String [] AudioArrray) {
         String [] MangSau=new String[AudioArrray.length-44];
         System.arraycopy(AudioArrray, 44, MangSau, 0,AudioArrray.length-44);
        return MangSau;
       
    }
    public String [] TachAudioThanhNhieuMau(int PIN, String [] audi) {
      
        StringBuilder audio= new StringBuilder();
        //gop mang audio thanh1 day
        for (int i=0;i<audi.length;i++)
        {
            audio.append(audi[i]);
        }
            //doistring buider thanh string
        String AUDIO=audio.toString();
        //tach tung phan tu cua day do
        String [] newau=AUDIO.split("");
        //kich thuoc tung mau tin (mau de giau)
        int MauTin = audio.length() /PIN;
        //
        String[] MangNho = new String[PIN+1];
        MangNho[0]="";
        // bien count 
        int count=0;
        // data/pin se duoc PIN đoạn,lấy tưng doan gan cho mang nho
        for (int j = 0; j < PIN; j++) 
        {
            MangNho[j]="";
            
            for (int k = j * MauTin; k < (j + 1) * MauTin; k++) 
            {
                count++;
                MangNho[j] +=newau[k];
                
            }
          
        }
        if (newau.length-count>0)
                {
                    String [] MangConLai=new String[1];
                    for (int i=0;i<1;i++)
                    {
                        MangConLai[i]="";
                       for (int ii=count;ii<newau.length;ii++)
                       {
                               MangConLai[i]+=newau[ii];
                        }
                    }
                   System.arraycopy(MangConLai, 0, MangNho, PIN, 1);
                }
        return MangNho;
                
    }
public StringBuilder checkParity( StringBuilder message, String [] MangNho)
{
   StringBuilder gom=new StringBuilder();
   String s=message.toString();//
   String [] thongdiep=s.split("");
   int PIN=thongdiep.length;
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
           if (count%2==0)// day chan
           {
               if (thongdiep[i].equals("1")==true)
               {
                   if (l[l.length-1].equals("1")==true)
                   {
                         l[l.length-1]="0";
                   }
                   else 
                   {                      
                       l[l.length-1]="1";
                   }
               }
            }
           if (count%2!=0)
           {
               if (thongdiep[i].equals("0")==true)
               {
                   if (l[l.length-1].equals("1")==true)
                   {
                       l[l.length-1]="0";
                    }
                  else 
                   {
                       l[l.length-1]="1";
                   }
               }
            }
         
            for (int ii=0;ii<l.length;ii++)
            {
                gom.append(l[ii]);
            }
    }
  
   {
       gom.append(MangNho[MangNho.length-1]);//phan du
   }

    return gom;

}

public int [] OutputAudio () throws IOException
{
      
        String sk[]=CovertAudioToBin();
        StringBuilder bonbonbyte= Tach44byteToNewArray(sk);
        StringBuilder mes=CovertTextToBinary(Messagetxt.getText());
        String ss=mes.toString();
        int PIN=ss.length();
        String [] s=CovertAudioToBin();
        String [] MangAudio= MangSau(s);
        String [] chiamau=TachAudioThanhNhieuMau(PIN, MangAudio);
         StringBuilder ll= checkParity(mes, chiamau);
         
        //Bước 2: Ghi dữ liệu
        //gop 44 byte dau voi cac byte con lai
        bonbonbyte.append(ll);
       //doi mang audio ra string
        String lp=bonbonbyte.toString();
        //tach mang string thanh tung  bit
        String ko []=lp.split("");
         //chieu dai moi mang bit la 8,
            //so mang la len
        int len=bonbonbyte.length()/8;
             //Khai báo 1 mảng lo, với kích thước len, chia mẫu audio thành từng mẫu 8 bit
        String [] lo=new String[len];
            for (int i=0;i<len;i++)
            { 
                lo[i]="";
                for (int j=i*8;j<(i+1)*8;j++)
                {
                    lo[i]+=ko[j];
                }
            }
            //khai báo mảng int để convert mảng string sang mảng int (mảng int mới có thể ghi ra ngoài file)
            int bb[]=new int[len];
            for (int i=0;i<len;i++)
             {
                 bb[i]=Integer.parseInt(lo[i],2);
             }

            
             b=new int [bb.length];
             System.arraycopy(bb, 0, b, 0, bb.length);
             
             return bb;

}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        SourceFileAudio = new javax.swing.JTextField();
        ChooseFilebt = new javax.swing.JButton();
        PlayAudiobt = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        Pausebt = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Messagetxt = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        Ouputbt = new javax.swing.JButton();
        keytxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(800, 351));
        setMinimumSize(new java.awt.Dimension(800, 351));
        setSize(new java.awt.Dimension(800, 351));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SourceFileAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SourceFileAudioActionPerformed(evt);
            }
        });
        jPanel1.add(SourceFileAudio, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 300, 27));

        ChooseFilebt.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChooseFilebt.setText("Choose");
        ChooseFilebt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseFilebtActionPerformed(evt);
            }
        });
        jPanel1.add(ChooseFilebt, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, -1, -1));

        PlayAudiobt.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        PlayAudiobt.setText("Play");
        PlayAudiobt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayAudiobtActionPerformed(evt);
            }
        });
        jPanel1.add(PlayAudiobt, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 69, -1));

        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Source ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, -1, -1));

        Pausebt.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Pausebt.setText("Stop");
        Pausebt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PausebtActionPerformed(evt);
            }
        });
        jPanel1.add(Pausebt, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, 70, -1));

        Messagetxt.setColumns(20);
        Messagetxt.setRows(5);
        jScrollPane1.setViewportView(Messagetxt);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 300, 60));

        jLabel3.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Message ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, -1, -1));

        Ouputbt.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Ouputbt.setText("Hide");
        Ouputbt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OuputbtActionPerformed(evt);
            }
        });
        jPanel1.add(Ouputbt, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, -1, 30));
        jPanel1.add(keytxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 300, 117, -1));

        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("KEY");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 300, 40, 20));

        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ENCRYPT");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, -1));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Close");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 320, -1, -1));

        jButton2.setFont(new java.awt.Font("Arial Black", 1, 11)); // NOI18N
        jButton2.setText("Save");
        jButton2.setPreferredSize(new java.awt.Dimension(57, 23));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 300, 70, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/test.jpg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 360));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SourceFileAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SourceFileAudioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SourceFileAudioActionPerformed

    private void ChooseFilebtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChooseFilebtActionPerformed

        JFileChooser fc = new JFileChooser();//doi tuong de chon file tu window
        FileNameExtensionFilter filterExten = new FileNameExtensionFilter("only use wav", "wav");
        fc.setFileFilter(filterExten);
        fc.showDialog(ChooseFilebt, "FileUpLoad");
        String path = SourceFileAudio.getText() + fc.getSelectedFile();//lay duong dan cua filed SourceFileAudio(pt getSelectedFile cua doi tuong fc)
        SourceFileAudio.setText(path);

        if (SourceFileAudio.getText().equals(path) && !path.equals("null")) {
            PlayAudiobt.setEnabled(true);
            Pausebt.setEnabled(true);
        }
    }//GEN-LAST:event_ChooseFilebtActionPerformed

    private void PlayAudiobtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayAudiobtActionPerformed
        InputStream in;
        try {
            in = new FileInputStream(SourceFileAudio.getText());
            as = new AudioStream(in);
            AudioPlayer.player.start(as);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParityCoding.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ParityCoding.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PlayAudiobtActionPerformed

    private void PausebtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PausebtActionPerformed

        if (as != null) {
            AudioPlayer.player.stop(as);
        }

    }//GEN-LAST:event_PausebtActionPerformed

    private void OuputbtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OuputbtActionPerformed
        try {
            OutputAudio();
            JOptionPane.showMessageDialog(rootPane, "Giấu tin thành công");
        } catch (IOException ex) {
            Logger.getLogger(ParityCoding.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_OuputbtActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      super.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     JFrame parentFrame = new JFrame();
 JFileChooser fileChooser = new JFileChooser();
fileChooser.setDialogTitle("Specify a file to save"); 
FileNameExtensionFilter filterExten = new FileNameExtensionFilter("only use wav", "wav");
fileChooser.setFileFilter(filterExten);
int userSelection = fileChooser.showSaveDialog(parentFrame);
 if (userSelection == JFileChooser.APPROVE_OPTION) {
    File fileToSave = fileChooser.getSelectedFile();
   
    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        try {
          
       
            String path=fileToSave.getAbsolutePath();
        FileOutputStream fos = new FileOutputStream(path);
      DataOutputStream dos = new DataOutputStream(fos);

                 for (int c : b) {
                 dos.write(c);
              }
      
        fos.close();
        dos.close();
      
       } catch (IOException ex) {
         ex.printStackTrace();
       } 
      

}
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ChooseFilebt;
    private javax.swing.JTextArea Messagetxt;
    private javax.swing.JButton Ouputbt;
    private javax.swing.JButton Pausebt;
    private javax.swing.JButton PlayAudiobt;
    private javax.swing.JTextField SourceFileAudio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField keytxt;
    // End of variables declaration//GEN-END:variables


    

}
