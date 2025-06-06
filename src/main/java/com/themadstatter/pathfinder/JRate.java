package com.themadstatter.pathfinder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.ImageIcon;

public class JRate extends JFrame implements ActionListener, FocusListener, KeyListener {
  private ButtonGroup ratingButtonGroup;
  private JButton button;
  private JLabel bottomRatingTerm;
  private JLabel idEntryInstructionsLabel;
  private JLabel leftScaleAnchorLabel;
  private JLabel progressLabel;
  private JLabel rightScaleAnchorLabel;
  private JLabel scaleImageLabel;
  private JLabel topRatingTerm;
  private JPanel bottomRatingTermPanel;
  private JPanel buttonPanel;
  private JPanel idPanel;
  private JPanel instructionsAreaPanel;
  private JPanel instructionsButtonPanel;
  private JPanel progressPanel;
  private JPanel scaleImagePanel;
  private JPanel scaleLabelPanel;
  private JPanel topRatingTermPanel;
  private JRadioButton[] ratingButtons;
  private JTextArea instructionsArea;
  private JTextField idEntryField;
  private String firstEnteredID;
  private String secondEnteredID;
  private String[][] termPresentationOrderIndices;
  private Vector<String> orderedTerms;
  private Vector<String> randomizedTerms;
  private boolean showingInstructions;
  private int completedComparisonsCount;
  private int totalComparisonsCount;
  private int[][] capturedRatings;
  private long endTime;
  private long startTime;

  private JRate() {
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    
    setTitle("JRate");
    setSize(800, 600);
    Dimension dim = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    setLocation((dim.width - 800) / 2, (dim.height - 600) / 2);
    setIconImage(new ImageIcon(getClass().getResource("/img/pencil.png")).getImage());
    
    getContentPane().setLayout(new GridLayout(1, 0));
    idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    getContentPane().add(idPanel);
    getID();
  }
  
  private void getID() {
    idEntryInstructionsLabel = new JLabel("Please Enter ID Number: ");
    idPanel.add(idEntryInstructionsLabel);
    
    idEntryField = new JTextField(9);
    idPanel.add(idEntryField);
    
    button = new JButton("Enter");
    idPanel.add(button);
    
    button.addActionListener(this);
    
    idEntryField.requestFocus();
  }
  
  private void getIDAgain() {
    idEntryInstructionsLabel.setText("Please Re-enter ID Number: ");
    idEntryField.setText("");
    button.setText("Re-enter");
    
    idEntryField.requestFocus();
  }
  
  private void getIDWrong() {
    idEntryInstructionsLabel.setText("IDs Did Not Match.  Please Enter ID Number: ");
    idEntryField.setText("");
    button.setText("Enter");
    
    idEntryField.requestFocus();
  }
  
  private void showStartingInstructions() {
    getContentPane().remove(idPanel);
    
    getContentPane().setLayout(new BorderLayout());
    instructionsAreaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    instructionsButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    getContentPane().add(instructionsAreaPanel, BorderLayout.CENTER);
    getContentPane().add(instructionsButtonPanel, BorderLayout.SOUTH);
    
    instructionsArea = new JTextArea(30, 65);
    instructionsArea.setLineWrap(true);
    instructionsArea.setWrapStyleWord(true);
    instructionsArea.setEditable(false);
    instructionsArea.setBackground(getBackground());
    
    JScrollPane jsp = new JScrollPane(instructionsArea);
    instructionsAreaPanel.add(jsp);
    instructionsAreaPanel.revalidate();
    
    EasyReader startingInstructionsReader;
    File startingInstructionsFile = new File("startingInstructions.txt");
    if (startingInstructionsFile.exists()) {
      startingInstructionsReader = new EasyReader("startingInstructions.txt");
    } else {
      startingInstructionsReader = new EasyReader(getClass().getResourceAsStream("/exp/startingInstructions.txt"));
    }
    while(!startingInstructionsReader.eof())
      instructionsArea.append(startingInstructionsReader.readLine() + "\n");
    instructionsArea.setText(instructionsArea.getText().substring(0, instructionsArea.getText().length() - 6));
    instructionsArea.setCaretPosition(0);
    
    button = new JButton("Go");
    button.addActionListener(this);
    instructionsButtonPanel.add(button);
    
    showingInstructions = true;
    
    addFocusListener(this);
    addKeyListener(this);
    requestFocus();
  }
  
  private void showEndingInstructions() {
    getContentPane().removeAll();
    
    getContentPane().setLayout(new BorderLayout());
    instructionsAreaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    instructionsButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    getContentPane().add(instructionsAreaPanel, BorderLayout.CENTER);
    getContentPane().add(instructionsButtonPanel, BorderLayout.SOUTH);
    
    instructionsArea = new JTextArea(30, 65);
    instructionsArea.setLineWrap(true);
    instructionsArea.setWrapStyleWord(true);
    instructionsArea.setEditable(false);
    instructionsArea.setBackground(getBackground());
    
    JScrollPane jsp = new JScrollPane(instructionsArea);
    instructionsAreaPanel.add(jsp);
    instructionsAreaPanel.revalidate();
        
    EasyReader endingInstructionsReader;
    File endingInstructionsFile = new File("endingInstructions.txt");
    if (endingInstructionsFile.exists()) {
      endingInstructionsReader = new EasyReader("endingInstructions.txt");
    } else {
      endingInstructionsReader = new EasyReader(getClass().getResourceAsStream("/exp/endingInstructions.txt"));
    }
    while (!endingInstructionsReader.eof())
      instructionsArea.append(endingInstructionsReader.readLine() + "\n");
    instructionsArea.setText(instructionsArea.getText().substring(0, instructionsArea.getText().length() - 6));
    
    instructionsArea.setCaretPosition(0);
    
    button = new JButton("Exit");
    button.addActionListener(this);
    instructionsButtonPanel.add(button);
    
    showingInstructions = true;
    
    addFocusListener(this);
    addKeyListener(this);
    requestFocus();
  }
  
  private void initializeRatingTask() {
    getContentPane().remove(instructionsAreaPanel);
    getContentPane().remove(instructionsButtonPanel);
    
    getContentPane().setLayout(new GridLayout(6, 1));
    
    JPanel scaleImagePanel = new JPanel(new BorderLayout());
    JPanel scaleLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
    JPanel topRatingTermPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel bottomRatingTermPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel progressPanel = new JPanel(new BorderLayout());
    
    getContentPane().add(scaleImagePanel);
    getContentPane().add(scaleLabelPanel);
    getContentPane().add(topRatingTermPanel);
    getContentPane().add(bottomRatingTermPanel);
    getContentPane().add(buttonPanel);
    getContentPane().add(progressPanel);
    
    scaleImageLabel = new JLabel("<---------------|---------------|---------------|---------------|---------------|---------------|---------------|---------------|---------------|--------------->");
    scaleImageLabel.setHorizontalAlignment(JLabel.CENTER);
    scaleImagePanel.add(scaleImageLabel, BorderLayout.CENTER);
    
    leftScaleAnchorLabel = new JLabel("Unrelated");
    rightScaleAnchorLabel = new JLabel("Related");
    
    scaleLabelPanel.add(leftScaleAnchorLabel);
    
    ratingButtonGroup = new ButtonGroup();
    ratingButtons = new JRadioButton[10];
    ratingButtons[0] = new JRadioButton();
    ratingButtonGroup.add(ratingButtons[0]);
    for (int i = 1; i <= 9; i++) {
      ratingButtons[i] = new JRadioButton(Integer.toString(i));
      ratingButtonGroup.add(ratingButtons[i]);
      scaleLabelPanel.add(ratingButtons[i]);
    }
    
    scaleLabelPanel.add(rightScaleAnchorLabel);
    
    topRatingTerm = new JLabel("Term 1");
    topRatingTerm.setFont(new Font("Serif", Font.PLAIN, 12));
    bottomRatingTerm = new JLabel("Term 2");
    bottomRatingTerm.setFont(new Font("Serif", Font.PLAIN, 12));
    
    topRatingTermPanel.add(topRatingTerm);
    bottomRatingTermPanel.add(bottomRatingTerm);
    
    button = new JButton("Next");
    button.setSize(100, 50);
    button.addActionListener(this);
    buttonPanel.add(button);
    
    progressLabel = new JLabel("X out of X completed");
    progressPanel.add(progressLabel, BorderLayout.SOUTH);
    progressLabel.setHorizontalAlignment(JLabel.RIGHT);
    
    orderedTerms = new Vector<>();
    randomizedTerms = new Vector<>();
    String term = new String();
    
    EasyReader termsReader;
    File termsFile = new File("terms.txt");
    if (termsFile.exists()) {
      termsReader = new EasyReader("terms.txt");
    } else {
      termsReader = new EasyReader(getClass().getResourceAsStream("/exp/terms.txt"));
    }
    while (!termsReader.eof()) {
      term = termsReader.readLine();
      orderedTerms.add(term);
      randomizedTerms.add(term);
    }
    orderedTerms.remove(null);
    randomizedTerms.remove(null);
    
    shuffleVector(randomizedTerms);
    
    totalComparisonsCount = orderedTerms.size() * (orderedTerms.size() - 1) / 2;
    completedComparisonsCount = 0;
    
    updateStatus(completedComparisonsCount, totalComparisonsCount);
    
    makeTermPresentationOrderIndices();
    shuffleArray(termPresentationOrderIndices);
    
    topRatingTerm.setText(termPresentationOrderIndices[0][0].toString());
    bottomRatingTerm.setText(termPresentationOrderIndices[0][1].toString());
    
    capturedRatings = new int[orderedTerms.size()][orderedTerms.size()];
    
    showingInstructions = false;
    
    startTime = System.currentTimeMillis();
    
    requestFocus();
  }
  
  private void showNextTermPair() {
    completedComparisonsCount++;
    if(completedComparisonsCount == totalComparisonsCount)
      return;
    double myran = Math.random();
    if(myran < 0.5) {
      topRatingTerm.setText(termPresentationOrderIndices[completedComparisonsCount][0].toString());
      bottomRatingTerm.setText(termPresentationOrderIndices[completedComparisonsCount][1].toString());
    }
    else{
      topRatingTerm.setText(termPresentationOrderIndices[completedComparisonsCount][1].toString());
      bottomRatingTerm.setText(termPresentationOrderIndices[completedComparisonsCount][0].toString());
    }
  }
  
  private void makeTermPresentationOrderIndices() {
    termPresentationOrderIndices = new String[totalComparisonsCount][2];
    int temp = 0;
    for(int first = 0; first < randomizedTerms.size()-1; first++)
      for(int last = first + 1; last < randomizedTerms.size(); last++) {
        termPresentationOrderIndices[temp][0] = randomizedTerms.get(first).toString();
        termPresentationOrderIndices[temp][1] = randomizedTerms.get(last).toString();
        temp++;
      }
  }
  
  private int getRating() {
    if(ratingButtons[1].isSelected())
      return 1;
    if(ratingButtons[2].isSelected())
      return 2;
    if(ratingButtons[3].isSelected())
      return 3;
    if(ratingButtons[4].isSelected())
      return 4;
    if(ratingButtons[5].isSelected())
      return 5;
    if(ratingButtons[6].isSelected())
      return 6;
    if(ratingButtons[7].isSelected())
      return 7;
    if(ratingButtons[8].isSelected())
      return 8;
    if(ratingButtons[9].isSelected())
      return 9;
    return 0;
  }
  
  private void updateStatus(int a, int b) {
    progressLabel.setText(a + " out of " + b + " completed.");
    if(a == b) {
      endTime = System.currentTimeMillis();
      writePrxFile();
      showEndingInstructions();
    }
  }
  
  private void shuffleVector(Vector<String> A) {
      for (int i = A.size() - 1; i > 1; i--) {
        int r = (int) (Math.random() * (i + 1));
        String tmp = A.get(r).toString();
        A.add(tmp);
        A.remove(r);
      }
  }
  
  private void shuffleArray(String[][] B) {
    for (int i = totalComparisonsCount - 1; i > 0; i--) {
      int r = (int) (Math.random() * (i + 1));
      String[] tmp = {B[r][0], B[r][1]};
      B[r][0] = B[i][0];
      B[r][1] = B[i][1];
      B[i][0] = tmp[0];
      B[i][1] = tmp[1];
    }
  }
  
  private void collectRating() {
    int[] termOrderedIndices = {
      orderedTerms.indexOf(topRatingTerm.getText()), 
      orderedTerms.indexOf(bottomRatingTerm.getText())
    };
    if(termOrderedIndices[0] < termOrderedIndices[1])
      capturedRatings[termOrderedIndices[0]][termOrderedIndices[1]] = getRating();
    else
      capturedRatings[termOrderedIndices[1]][termOrderedIndices[0]] = getRating();
  }
  
  private void clearRating() {
    ratingButtons[0].setSelected(true);
    requestFocus();
  }
    
  private void writePrxFile() {
    Date d = new Date(startTime);
    SimpleDateFormat sd = new SimpleDateFormat();
    long elapsedTime = endTime - startTime;
    
    File prxFile = new File(firstEnteredID + ".prx");
    for(int i = 1; prxFile.exists(); i++)
      prxFile = new File(firstEnteredID + i + ".prx");
    EasyWriter prxFileWriter = new EasyWriter(prxFile.toString());
    for (int row = 0; row < orderedTerms.size(); row++) {
      if (row < 1) {
        prxFileWriter.println("DATA:  " + prxFile.toString() + "     DATE:  " + sd.format(d) + "     ELTIME(MilliSecs):  " + elapsedTime);
        prxFileWriter.println("similarities");
        prxFileWriter.println(orderedTerms.size() + " items");
        prxFileWriter.println("0 decimals");
        prxFileWriter.println("1 min");
        prxFileWriter.println("9 max");
        prxFileWriter.println("lower triangle:");
      }
      if (row > 1)
        prxFileWriter.println();
      for (int col = 0; col < orderedTerms.size(); col++)
        if (capturedRatings[col][row] != 0) {
          prxFileWriter.print(" ");
          prxFileWriter.print(capturedRatings[col][row]);
        }
    }
    prxFileWriter.close();
  }
  
  private boolean ratingAvailable() {
    if(getRating() == 0)
      return false;
    else
      return true;
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Next")) {
      if(ratingAvailable()) {
        collectRating();
        clearRating();
        showNextTermPair();
        updateStatus(completedComparisonsCount, totalComparisonsCount);
      }
    }
     
    if (e.getActionCommand().equals("Enter")) {
      firstEnteredID = idEntryField.getText();
      getIDAgain();
    }
    
    if (e.getActionCommand().equals("Re-enter")) {
      secondEnteredID = idEntryField.getText();
      if(firstEnteredID.equals(secondEnteredID))
        showStartingInstructions();
      else
        getIDWrong();
    }
    
    if (e.getActionCommand().equals("Go")) {
      initializeRatingTask();
    }
    
    if (e.getActionCommand().equals("Exit")) {
      dispose();
    }
  }
  
  public void keyReleased(KeyEvent evt) {
    int key = evt.getKeyCode();
    if (!showingInstructions) {
      if (key == KeyEvent.VK_1)
        ratingButtons[1].setSelected(true);
      if (key == KeyEvent.VK_2)
        ratingButtons[2].setSelected(true);
      if (key == KeyEvent.VK_3)
        ratingButtons[3].setSelected(true);
      if (key == KeyEvent.VK_4)
        ratingButtons[4].setSelected(true);
      if (key == KeyEvent.VK_5)
        ratingButtons[5].setSelected(true);
      if (key == KeyEvent.VK_6)
        ratingButtons[6].setSelected(true);
      if (key == KeyEvent.VK_7)
        ratingButtons[7].setSelected(true);
      if (key == KeyEvent.VK_8)
        ratingButtons[8].setSelected(true);
      if (key == KeyEvent.VK_9)
        ratingButtons[9].setSelected(true);
    }
    if (key == KeyEvent.VK_SPACE)
      button.doClick();
    if (key == KeyEvent.VK_ENTER)
      button.doClick();
  }
  
  public void keyPressed(KeyEvent evt){
  }
  
  public void keyTyped(KeyEvent evt) {
  }
  
  public void focusLost(FocusEvent evt) {
  }
  
  public void focusGained(FocusEvent evt) {
  }
  
  public static void main(String[] args) {
    new JRate().setVisible(true);
  }
}
