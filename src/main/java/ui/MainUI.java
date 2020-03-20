package ui;

import config.Common;
import utils.ClipboardOperation;
import utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class MainUI extends JFrame {

    JFrame f = new JFrame("UploadImage");
    ImagePanel imagePanel = new ImagePanel();

    MyOnClickListener onClickListener = new MyOnClickListener();
    JPanel panel = new JPanel();
    JButton upLoadFromLocalFile = new JButton("FromFile");
    JButton upLoadFromClipBoard = new JButton("FromClipboard");
    JMenuItem mHelpItem = new JMenuItem("Help");

    JLabel uploadHistoryList = new JLabel("History");
    public MainUI(String title) throws HeadlessException {
        super(title);
        init();
    }

    public void init() {

        JPanel mRightPanel = new JPanel();
        f.setSize(640, 480);

        f.setLocation(200, 200);

        JMenuBar menuBar = new JMenuBar();

        JMenu mSettingItem = new JMenu("Setting");
        JMenu mAboutItem = new JMenu("About");



        mHelpItem.addActionListener(onClickListener);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mSettingItem.add(new JMenuItem("Configuration"));
        mSettingItem.addSeparator();
        mSettingItem.add(exitItem);
        mAboutItem.add(mHelpItem);
        mAboutItem.add(new JMenuItem("About Me"));

        uploadHistoryList.setPreferredSize(new Dimension(80,15));

        menuBar.add(mSettingItem);
        menuBar.add(mAboutItem);

        upLoadFromClipBoard.addActionListener(onClickListener);
        upLoadFromLocalFile.addActionListener(onClickListener);
        imagePanel.add(upLoadFromClipBoard);
        imagePanel.add(upLoadFromLocalFile);
        imagePanel.add(upLoadFromLocalFile);
        imagePanel.setBackground(Color.BLUE);
        mRightPanel.add(uploadHistoryList);
//        mRightPanel.setLayout();
        imagePanel.setPreferredSize(new Dimension(480,480));

        f.add(imagePanel, BorderLayout.WEST);
        f.add(mRightPanel,BorderLayout.EAST);
//        f.add(panel);

//        f.add(imagePanel);
        f.setJMenuBar(menuBar);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setVisible(true);
    }

    class MyOnClickListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "FromFile":
                    JOptionPane.showMessageDialog(f, "开发中。。。");

                    ClipboardOperation.writeStringToClipboard("123");
                    break;
                case "FromClipboard":
                    imagePanel.loadPhoto(ClipboardOperation.readImageFromClipboard());
                default:
                    saveImage();
                    System.out.println(ClipboardOperation.readStringFromClipboard());
            }
        }
    }

    class ImagePanel extends JPanel {
        private Image image;

        //初始化时，加载的图片1.jpg
        public ImagePanel() {
            try {
                image = ImageIO.read(new File("D:\\Pictures\\Head\\bloglogo.jpg"));

            } catch (IOException e) {
                e.getStackTrace();
            }
        }


        public void loadPhoto(Image img) {
            image = img;
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (image == null) {
                return;
            }

            int imageWidth = image.getWidth(this);
            int imageHeight = image.getHeight(this);

//将图片画在左上角
            g.drawImage(image, 20, 50, null);

        }
    }

    public void saveImage(){
        try {
            Image image = ClipboardOperation.readImageFromClipboard();
            File file = new File(Common.USER_HOME_DIR + Common.TEMP_FILE_PATH + Common.TEMP_IMAGE_NAME_PNG);
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = bufferedImage.createGraphics();
            graphics2D.drawImage(image,null,null);
            ImageIO.write((RenderedImage)bufferedImage,"png",file);
            Utils.uploadImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}