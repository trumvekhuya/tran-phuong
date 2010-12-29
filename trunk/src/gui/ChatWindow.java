package gui;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.text.DefaultCaret;


import common.BlockingQueue;
import common.Tags;
import common.outputHandler;
import fileHandler.FileHandler;

public class ChatWindow extends javax.swing.JFrame implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	// private BufferedReader reader;
	private PrintWriter writer;
	private JFileChooser fc;
	private Task task;
	outputHandler vp;
        private String name;
        BlockingQueue<String> qe =  new BlockingQueue<String>();

	/** Creates new form ChatWindow */
	public ChatWindow() {
		initComponents();
	}

	public ChatWindow(String name, InputStream is, OutputStream os)
			throws InterruptedException, UnsupportedEncodingException {
		super();
                this.name = name;
		vp = new outputHandler(qe, os);
		vp.start();
		Thread.sleep(10);
		initComponents();
	}
	class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            while (progress < 100) {
                //Sleep for up to one second.
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException ignore) {}
                //Make random progress.
                progress += random.nextInt(10);
                setProgress(Math.min(progress, 100));
            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            sendFileButton.setEnabled(true);
            setCursor(null); //turn off the wait cursor
            displayTX.append("Done!\n");
        }
    }

	private void initComponents() {

		sendFileButton = new javax.swing.JButton();
		sendButton = new javax.swing.JButton();
		text = new javax.swing.JScrollPane();
		textTX = new javax.swing.JTextArea();
		panel = new javax.swing.JScrollPane();
		displayTX = new javax.swing.JTextArea();
		DefaultCaret caret = (DefaultCaret) displayTX.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		jProgressBar1 = new javax.swing.JProgressBar();
		jProgressBar1.setValue(0);
		jProgressBar1.setStringPainted(true);
		// setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		sendFileButton.setText("SendFile");
		sendFileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				sendFileButtonActionPerformed(evt);
			}
		});

		sendButton.setText("Send");
		sendButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				sendButtonActionPerformed(evt);
			}
		});

		textTX.setColumns(20);
		textTX.setRows(5);
		text.setViewportView(textTX);

		displayTX.setColumns(20);
		displayTX.setEditable(false);
		displayTX.setRows(5);
		panel.setViewportView(displayTX);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																false)
														.addComponent(panel)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																layout
																		.createSequentialGroup()
																		.addComponent(
																				text,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				292,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				sendButton))
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																layout
																		.createSequentialGroup()
																		.addComponent(
																				sendFileButton)
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				jProgressBar1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												panel,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												165,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGap(
																				10,
																				10,
																				10)
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jProgressBar1,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								sendFileButton))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				text,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				54,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGap(
																				45,
																				45,
																				45)
																		.addComponent(
																				sendButton)))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		pack();
	}// </editor-fold>
	
	
	public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            jProgressBar1.setValue(progress);
        } 
    }
	private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		String text = textTX.getText();
		display(name + ": " + text);

		textTX.selectAll();
		textTX.setText("");
		// Make sure the new text is visible, even if there
		// was a selection in the text area
		String msg = Tags.OPEN_SEND + converString(text) + Tags.END_SEND;
		qe.push(msg);
		System.out.println("add");
		// writer.print(Tags.OPEN_SEND + text + Tags.END_SEND); ///
		// NOTICE!!!/////////
		// writer.flush(); ////////////////////
	}

	private void sendFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// This is where a real application would open the file.
			display("Opening: " + file.getName() + "." + "\n");

			String f_req = Tags.OPEN_FILE_CONN + file.getName()
					+ Tags.END_FILE_CONN;

			qe.push(f_req);
			System.out.println("add");
			
			displayTX.setCaretPosition(textTX.getDocument().getLength());
			sendFileButton.setEnabled(false);
	       // setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	        //Instances of javax.swing.SwingWorker are not reusuable, so
	        //we create new instances as needed.
	        task = new Task();
	        task.addPropertyChangeListener(this);
	        task.execute();
	        
	        
			FileHandler sendFile = new FileHandler(file.getAbsolutePath(), qe);
			sendFile.start();
		} else {
			// log.append("Open command cancelled by user." + "\n");
		}
		displayTX.setCaretPosition(displayTX.getDocument().getLength());

	}

	public void display(String s) {
		displayTX.append(s + "\n");

	}

	public void windowClosed(WindowEvent e) {
		System.out.println("out!!!");
		writer.close();
	}

	private String converString(String mess) {
		String result = "";
		char[] arr = mess.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			result += arr[i];
			if (arr[i] == '<' || arr[i] == '>')
				result += arr[i];
		}
		return result;
	}

	// public static void main(String args[]) {
	// java.awt.EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// new ChatWindow().setVisible(true);
	// }
	// });
	// }

	// Variables declaration - do not modify
	private javax.swing.JTextArea displayTX;
	private javax.swing.JProgressBar jProgressBar1;
	private javax.swing.JScrollPane panel;
	private javax.swing.JButton sendButton;
	private javax.swing.JButton sendFileButton;
	private javax.swing.JScrollPane text;
	private javax.swing.JTextArea textTX;
	// End of variables declaration

}
