package utils;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class ClipboardOperation {
    public static void writeStringToClipboard(String content) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection(content);
        clipboard.setContents(transferable, null);
    }

    public static String readStringFromClipboard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        Transferable transferable = clipboard.getContents(null);
        if (transferable != null) {
            if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    return (String) transferable.getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {

                }
            }
        }
        return null;
    }

    public static void writeImageToClipBoard(final Image image) {
        Transferable transferable = new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{DataFlavor.imageFlavor};
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return DataFlavor.imageFlavor.equals(flavor);
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                if (isDataFlavorSupported(flavor)) {
                    return image;
                }
                return null;
            }
        };
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferable, null);

    }

    public static Image readImageFromClipboard(){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = clipboard.getContents(null);
        if(transferable!=null){
            if(transferable.isDataFlavorSupported(DataFlavor.imageFlavor)){
                try {
                    return (Image) transferable.getTransferData(DataFlavor.imageFlavor);
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
