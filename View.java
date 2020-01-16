/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.text.DecimalFormat;
import javax.swing.JOptionPane.*;
public class View {
    private final JFrame containerFrame;
    private final Color black = new Color(0,0,0);
    private final Color graaay = new Color(101,103,114);
    private final Color gray = new Color(191,193,204);
    private final Color white = new Color(255,255,255);
    private final Color yellow = new Color(141,173,184);
    private final Color blue = new Color(0,0,255);
    private final Color green = new Color(247,120,107);
    private final Color red = new Color(255,0,0);
    private final Color ltGreen = new Color(81,174,163);
    private final Color transparent = new Color(0,0,0,0);
    private final JPanel menuPanel;
    private final JPanel topMenuPanel;
    private final JPanel algoPanel;
    private final JPanel difficultyPanel;
    private final JPanel btnStartPanel;
    private final JPanel btnGeneratePanel;
    private final JPanel btnResetPanel;
    private final JPanel btnAboutPanel;
    private final JPanel speedSliderPanel;
    private final JPanel speedLowHighPanel;
    private final JButton btnStart;
    private final JButton btnGenerate;
    private final JButton btnReset;
    private final JButton btnAbout;
    private final ImageIcon btnStartIcon;
    private final ImageIcon btnGenerateIcon;
    private final ImageIcon btnResetIcon;
    private final ImageIcon btnAboutIcon;
    private final ImageIcon btnStartIconHover;
    private final ImageIcon btnGenerateIconHover;
    private final ImageIcon btnResetIconHover;
    private final ImageIcon btnAboutIconHover;
    private final ImageIcon btnStartIconClicked;
    private final ImageIcon btnGenerateIconClicked;
    private final ImageIcon btnResetIconClicked;
    private final ImageIcon btnAboutIconClicked;
    private final JLabel bg;
    private final JLabel algoLabel;
    private final JLabel speedLabel;
    private final JLabel lowSpeedLabel;
    private final JLabel highSpeedLabel;
    private final JComboBox<String> difficultyCBox;
    public final JSlider speedSlider;
    public final ButtonGroup algorGroupButton;
    public final JRadioButton aStarRB;
    public final JRadioButton bfsRB;
    public final JRadioButton dfsRB;
    private JLabel cellLabel[][];
    private int arrayStatus[][];
    private boolean expanded[][];
    private int xFinish;
    private int yFinish;
    private int xStart;
    private int yStart;
    private Node min;
    private Node start;
    private int SIZE = 15;
    private int cellWidth;
    private int cellHeight;
    private JPanel mazePanel;
    private ImageIcon carImage;
    private int solveSpeed;

    private class buttonListener extends MouseAdapter{
        private JButton btn;
        private ImageIcon btnDefault;
        private ImageIcon btnHover;
        private ImageIcon btnClicked;

        buttonListener(JButton btn, ImageIcon btnDefault, ImageIcon btnHover, ImageIcon btnClicked){
            super();
            this.btn = btn;
            this.btnDefault = btnDefault;
            this.btnHover = btnHover;
            this.btnClicked = btnClicked;
        }
        public void mousePressed(MouseEvent e){
            btn.setIcon(btnClicked);
        }
        public void mouseReleased(MouseEvent e){
            btn.setIcon(btnHover);
        }
        public void mouseEntered(MouseEvent e){
            btn.setIcon(btnHover);
        }
        public void mouseExited(MouseEvent e){
            btn.setIcon(btnDefault);
        }

    }

    private class Solve implements Runnable{
        private View v;
        
        public Solve(View v){
            this.v = v;
        }

        @Override
        public void run(){
            if(v.aStarRB.isSelected()){
                v.aStarSolve();
            }
            else if(v.bfsRB.isSelected()){
                v.bfsSolve();
            }
            else if(v.dfsRB.isSelected()){
                v.dfsSolve();
            }
            v.setControlEnabled(true);
        }
    }

    private class btnActionListener implements ActionListener{
        private View v;
        private String btnName;

        public btnActionListener(View v, String btnName){
            this.v = v;
            this.btnName = btnName;
        }

        public void actionPerformed(ActionEvent e){

            switch(btnName){
                case "start":
                    this.v.resetMaze();
                    this.v.solveSpeed = 1000 - this.v.speedSlider.getValue();
                    this.v.setControlEnabled(false);
                    new Thread(new Solve(v)).start();
                    break;
                case "generate":
                    this.v.genMaze();
                    break;
                case "reset":
                    this.v.resetMaze();
                    break;
                case "about":
                    JOptionPane.showMessageDialog(null, "How To Play: \n 1. Select Difficulty \n 2. Click Generate button to make a new maze with selected difficulty \n 3. Select Algorithm to find a way out of the Maze \n 4. Set path finding animation speed in the Slider \n 5. Click Start button to start path finding \n 6. Click Reset to reset all the path that has been checked \n\n\n Developed by: \n1. Ahmad Maulana Ari Septian\n2. Alif Sabrani\n3. Fuad Fadlila Surenggana\n4. M. Imam Syarwani\n5. Rijalul Imam\n\n\n\nTeknik Informatika\nUniversitas Mataram");
                    break;
            }
        }
    }

    public View(){
        containerFrame = new JFrame();
        bg = new JLabel(new ImageIcon(getClass().getResource("assets/images/frameBackground.jpg")));
        containerFrame.setContentPane(bg);
        containerFrame.setLayout(new BorderLayout());
        containerFrame.setTitle("Maze Escape");
        containerFrame.setResizable(false);
        containerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        containerFrame.setSize(1000, 650);

        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(300, 525));
        menuPanel.setMaximumSize(new Dimension(300, 525));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(125, 10, 0, 10));
        menuPanel.setOpaque(false);

        topMenuPanel = new JPanel(new GridLayout(4,1));
        btnStartIcon = new ImageIcon(getClass().getResource("assets/images/btnStart.png"));
        btnStartIconHover = new ImageIcon(getClass().getResource("assets/images/btnStartHover.png"));
        btnStartIconClicked = new ImageIcon(getClass().getResource("assets/images/btnStartClicked.png"));
        btnStart = new JButton(btnStartIcon);

        btnGenerateIcon = new ImageIcon(getClass().getResource("assets/images/btnGenerate.png"));
        btnGenerateIconHover = new ImageIcon(getClass().getResource("assets/images/btnGenerateHover.png"));
        btnGenerateIconClicked = new ImageIcon(getClass().getResource("assets/images/btnGenerateClicked.png"));
        btnGenerate = new JButton(btnGenerateIcon);

        btnResetIcon = new ImageIcon(getClass().getResource("assets/images/btnReset.png"));
        btnResetIconHover = new ImageIcon(getClass().getResource("assets/images/btnResetHover.png"));
        btnResetIconClicked = new ImageIcon(getClass().getResource("assets/images/btnResetClicked.png"));
        btnReset = new JButton(btnResetIcon);

        btnAboutIcon = new ImageIcon(getClass().getResource("assets/images/btnAbout.png"));
        btnAboutIconHover = new ImageIcon(getClass().getResource("assets/images/btnAboutHover.png"));
        btnAboutIconClicked = new ImageIcon(getClass().getResource("assets/images/btnAboutClicked.png"));
        btnAbout = new JButton(btnAboutIcon);

        btnStart.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnGenerate.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnReset.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnAbout.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        GridBagConstraints vCenter = new GridBagConstraints();
        btnStartPanel = new JPanel(new GridBagLayout());
        btnGeneratePanel = new JPanel(new GridBagLayout());
        btnResetPanel = new JPanel(new GridBagLayout());
        btnAboutPanel = new JPanel(new GridBagLayout());
        btnStartPanel.setOpaque(false);
        btnGeneratePanel.setOpaque(false);
        btnResetPanel.setOpaque(false);
        btnAboutPanel.setOpaque(false);

        btnStart.addMouseListener(new buttonListener(btnStart, btnStartIcon, btnStartIconHover, btnStartIconClicked));
        btnReset.addMouseListener(new buttonListener(btnReset, btnResetIcon, btnResetIconHover, btnResetIconClicked));
        btnGenerate.addMouseListener(new buttonListener(btnGenerate, btnGenerateIcon, btnGenerateIconHover, btnGenerateIconClicked));
        btnAbout.addMouseListener(new buttonListener(btnAbout, btnAboutIcon, btnAboutIconHover, btnAboutIconClicked));
        
        btnGenerate.addActionListener(new btnActionListener(this, "generate"));
        btnStart.addActionListener(new btnActionListener(this, "start"));
        btnReset.addActionListener(new btnActionListener(this, "reset"));
        btnAbout.addActionListener(new btnActionListener(this, "about"));

        btnStartPanel.add(btnStart, vCenter);
        btnGeneratePanel.add(btnGenerate, vCenter);
        btnResetPanel.add(btnReset, vCenter);
        btnAboutPanel.add(btnAbout, vCenter);

        String[] difficulty = {"Very Easy", "Easy", "Medium", "Hard", "Very Hard"};
        difficultyCBox = new JComboBox<>(difficulty);
        difficultyCBox.setFont(new Font("NewsGoth BT", Font.BOLD, 14));
        difficultyCBox.setSelectedIndex(0);
        difficultyCBox.setPreferredSize(new Dimension(159,37));
        difficultyCBox.setMaximumSize(new Dimension(159,37));
        difficultyCBox.setForeground(white);
        difficultyCBox.setBackground(ltGreen);
        difficultyCBox.setOpaque(false);
        difficultyPanel = new JPanel(new GridLayout(2,1));
        difficultyPanel.setOpaque(false);
        JLabel difficultyLabel = new JLabel("Maze Difficulty");
        difficultyLabel.setForeground(white);
        difficultyLabel.setFont(new Font("NewsGoth BT", Font.BOLD, 14));
        difficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        difficultyPanel.add(difficultyLabel);
        difficultyPanel.add(difficultyCBox);
        
        topMenuPanel.setPreferredSize(new Dimension(159,200));
        topMenuPanel.setMaximumSize(new Dimension(159,200));
        topMenuPanel.add(btnStartPanel);
        topMenuPanel.add(btnResetPanel);
        topMenuPanel.add(difficultyPanel);
        topMenuPanel.add(btnGeneratePanel);
        topMenuPanel.setOpaque(false);
        topMenuPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        menuPanel.add(topMenuPanel);

        algoPanel = new JPanel(new GridLayout(6,1));
        algoPanel.setOpaque(false);
        algoPanel.setBorder(BorderFactory.createLineBorder(white, 2, true));
        algoLabel = new JLabel("ALGORITHM");
        algoLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        algoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        algoLabel.setForeground(white);

        speedLabel = new JLabel("Animation Speed");
        speedLabel.setFont(new Font("NewsGoth BT", Font.BOLD, 16));
        speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        speedLabel.setForeground(white);

        speedSlider = new JSlider(0,1000);
        speedSlider.setOpaque(false);
        speedSliderPanel = new JPanel(new GridLayout(2,1));
        speedLowHighPanel = new JPanel(new GridLayout(1,2));
        lowSpeedLabel = new JLabel("Low");
        highSpeedLabel = new JLabel("High");
        speedSliderPanel.setOpaque(false);
        speedLowHighPanel.setOpaque(false);
        lowSpeedLabel.setOpaque(false);
        lowSpeedLabel.setFont(new Font("NewsGoth BT", Font.BOLD, 12));
        lowSpeedLabel.setHorizontalAlignment(SwingConstants.LEFT);
        lowSpeedLabel.setForeground(white);
        highSpeedLabel.setOpaque(false);
        highSpeedLabel.setFont(new Font("NewsGoth BT", Font.BOLD, 12));
        highSpeedLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        highSpeedLabel.setForeground(white);
        speedLowHighPanel.add(lowSpeedLabel);
        speedLowHighPanel.add(highSpeedLabel);
        speedSliderPanel.add(speedLowHighPanel);
        speedSliderPanel.add(speedSlider);

        aStarRB = new JRadioButton("A*");
        bfsRB = new JRadioButton("BFS");
        dfsRB = new JRadioButton("DFS");
        aStarRB.setIcon(new ImageIcon(getClass().getResource("assets/images/radioButton.png")));
        bfsRB.setIcon(new ImageIcon(getClass().getResource("assets/images/radioButton.png")));
        dfsRB.setIcon(new ImageIcon(getClass().getResource("assets/images/radioButton.png")));
        aStarRB.setSelectedIcon(new ImageIcon(getClass().getResource("assets/images/radioButtonSelected.png")));
        bfsRB.setSelectedIcon(new ImageIcon(getClass().getResource("assets/images/radioButtonSelected.png")));
        dfsRB.setSelectedIcon(new ImageIcon(getClass().getResource("assets/images/radioButtonSelected.png")));
        aStarRB.setOpaque(false);
        dfsRB.setOpaque(false);
        bfsRB.setOpaque(false);
        aStarRB.setFont(new Font("NewsGoth BT", Font.BOLD, 14));
        dfsRB.setFont(new Font("NewsGoth BT", Font.BOLD, 14));
        bfsRB.setFont(new Font("NewsGoth BT", Font.BOLD, 14));
        aStarRB.setForeground(white);
        bfsRB.setForeground(white);
        dfsRB.setForeground(white);
        aStarRB.setSelected(true);
        algorGroupButton = new ButtonGroup();
        algorGroupButton.add(aStarRB);
        algorGroupButton.add(bfsRB);
        algorGroupButton.add(dfsRB);
        algoPanel.add(algoLabel);
        algoPanel.add(aStarRB);
        algoPanel.add(bfsRB);
        algoPanel.add(dfsRB);
        algoPanel.add(speedLabel);
        algoPanel.add(speedSliderPanel);
        algoPanel.setOpaque(false);
        algoPanel.setPreferredSize(new Dimension(159,200));
        algoPanel.setMaximumSize(new Dimension(159,200));

        menuPanel.add(algoPanel);

        menuPanel.add(btnAboutPanel);
        containerFrame.add(menuPanel, BorderLayout.WEST);
        containerFrame.pack();
        solveSpeed = 1000 - speedSlider.getValue();
        this.genMaze();
    }
    
    public void setControlEnabled(boolean enabled){
        btnStart.setEnabled(enabled);
        btnGenerate.setEnabled(enabled);
        btnReset.setEnabled(enabled);
        difficultyCBox.setEnabled(enabled);
        speedSlider.setEnabled(enabled);
    }

    public void genMaze(){
        if(mazePanel != null){
            containerFrame.remove(mazePanel);    
        }
        
        Random r = new Random();
        int difficulty = difficultyCBox.getSelectedIndex();
        switch(difficulty){
            case 0:
                SIZE = 9;
                carImage = new ImageIcon(getClass().getResource("assets/images/Mazef1.gif"));
                break;
            case 1:
                SIZE = 15;
                carImage = new ImageIcon(getClass().getResource("assets/images/Mazef2.gif"));
                break;
            case 2:
                SIZE = 21;
                carImage = new ImageIcon(getClass().getResource("assets/images/Mazef3.gif"));
                break;
            case 3:
                SIZE = 27;
                carImage = new ImageIcon(getClass().getResource("assets/images/Mazef4.gif"));
                break;
            case 4:
                SIZE = 35;
                carImage = new ImageIcon(getClass().getResource("assets/images/Mazef5.gif"));
                break;
        }

        if(SIZE % 2 == 0){
            SIZE++;
        }
        SIZE += 2;
        mazePanel = new JPanel(new GridLayout(SIZE,SIZE));
        mazePanel.setPreferredSize(new Dimension(700, 625));
        mazePanel.setMaximumSize(new Dimension(700, 625));
        mazePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mazePanel.setOpaque(false);
        cellLabel = new JLabel[SIZE][SIZE];
        arrayStatus = new int[SIZE][SIZE];
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                cellLabel[x][y] = new JLabel();
                cellLabel[x][y].setOpaque(true);
                cellLabel[x][y].setFont(new Font("NewsGoth BT", Font.PLAIN, 8));
                cellLabel[x][y].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                if(x == 0 || x == SIZE - 1 || y == 0 || y == SIZE - 1){
                    cellLabel[x][y].setOpaque(false);
                    if(x == 0 && y != 0 && y != SIZE - 1){
                        cellLabel[x][y].setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, white));
                    }
                    else if(x == SIZE - 1 && y != 0 && y != SIZE - 1){
                        cellLabel[x][y].setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, white));
                    }
                    else if(y == 0 && x != 0 && x != SIZE - 1){
                        cellLabel[x][y].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, white));
                    }
                    else if(y == SIZE - 1 && x != 0 && x != SIZE - 1){
                        cellLabel[x][y].setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, white));    
                    }
                }
                else{
                    //cellLabel[x][y].setIcon(new ImageIcon(getClass().getResource("assets/images/building2.png")));
                    cellLabel[x][y].setBackground(graaay);
                }
                cellLabel[x][y].setHorizontalAlignment(SwingConstants.CENTER);
                arrayStatus[x][y] = 1;
            }
        }
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                mazePanel.add(cellLabel[x][y]);
            }
        }

        containerFrame.add(mazePanel, BorderLayout.CENTER);
        this.containerFrame.setVisible(true);
        cellWidth = (this.containerFrame.getWidth() - 310) / SIZE;
        cellHeight = this.containerFrame.getHeight() / SIZE;
        containerFrame.setLocationRelativeTo(null);

        int x, y;
        x = r.nextInt(SIZE);
        y = r.nextInt(SIZE);
        while(x % 2 == 0){
            x = r.nextInt(SIZE);
        }
        while(y % 2 == 0){
            y = r.nextInt(SIZE);
        }
        visitNeighbor(x, y);

        /*xStart = r.nextInt(SIZE);
        yStart = r.nextInt(SIZE);
        xFinish = r.nextInt(SIZE);
        yFinish = r.nextInt(SIZE);
        while(arrayStatus[xStart][yStart] != 0){
            while(xStart % 2 == 0){
                xStart = r.nextInt(SIZE);
            }
            while(yStart % 2 == 0){
                yStart = r.nextInt(SIZE);
            }
        }
        do{
            while(xFinish % 2 == 0 || xFinish == xStart){
                xFinish = r.nextInt(SIZE);
            }
            while(yFinish % 2 == 0 || yFinish == yStart){
                yFinish = r.nextInt(SIZE);
            }
        }
        while(arrayStatus[xFinish][yFinish] != 0);*/
        this.xStart = SIZE - 2;
        this.yStart = 1;
        this.xFinish = 1;
        this.yFinish = SIZE - 2;
        cellLabel[xStart][yStart].setOpaque(false);
        cellLabel[xFinish][yFinish].setOpaque(true);
        cellLabel[xStart][yStart].setIcon(carImage);
        cellLabel[xFinish][yFinish].setBackground(green);
    }

    public int[] getDirection(){
        ArrayList<Integer> direction = new ArrayList<>();
        int arr[] = new int[4];
        for(int i = 0;i < 4;i++){
            direction.add(i+1);
        }
        Collections.shuffle(direction);
        for(int i = 0;i < 4;i++){
            arr[i] = direction.get(i);
        }
        return arr;
    }

    public void visitNeighbor(int x, int y){
        int[] dir = getDirection();
        for(int i = 0; i < dir.length; i++){
            if(dir[i] == 1){
                if(x - 2 <= 0 || arrayStatus[x-2][y] == 0){
                    continue;
                }
                else{
                    arrayStatus[x-2][y] = 0;
                    arrayStatus[x-1][y] = 0;
                    cellLabel[x-1][y].setIcon(null);
                    cellLabel[x-2][y].setIcon(null);
                    cellLabel[x-1][y].setOpaque(false);
                    cellLabel[x-2][y].setOpaque(false);
                    visitNeighbor(x-2, y);
                }
            }
            else if(dir[i] == 2){
                if(x + 2 >= SIZE || arrayStatus[x+2][y] == 0){
                    continue;
                }
                else{
                    arrayStatus[x+1][y] = 0;
                    arrayStatus[x+2][y] = 0;
                    cellLabel[x+1][y].setIcon(null);
                    cellLabel[x+2][y].setIcon(null);
                    cellLabel[x+1][y].setOpaque(false);
                    cellLabel[x+2][y].setOpaque(false);
                    visitNeighbor(x+2, y);
                }
            }
            else if(dir[i] == 3){
                if(y - 2 <= 0 || arrayStatus[x][y-2] == 0){
                    continue;
                }
                else{
                    arrayStatus[x][y-1] = 0;
                    arrayStatus[x][y-2] = 0;
                    cellLabel[x][y-1].setIcon(null);
                    cellLabel[x][y-2].setIcon(null);
                    cellLabel[x][y-1].setOpaque(false);
                    cellLabel[x][y-2].setOpaque(false);
                    visitNeighbor(x, y-2);
                }
            }
            else{
                if(y + 2 >= SIZE || arrayStatus[x][y+2] == 0){
                    continue;
                }
                else{
                    arrayStatus[x][y+1] = 0;
                    arrayStatus[x][y+2] = 0;
                    cellLabel[x][y+1].setIcon(null);
                    cellLabel[x][y+2].setIcon(null);
                    cellLabel[x][y+1].setOpaque(false);
                    cellLabel[x][y+2].setOpaque(false);
                    visitNeighbor(x, y+2);
                }
            }
        }
    }

    public void aStarSolve(){
        PriorityQueue q = new PriorityQueue();
        expanded = new boolean[SIZE][SIZE];
        start = new Node(xStart, yStart, 0, getEuclideanDistance(xStart, yStart));
        q.start = this.start;
        q.push(start);
        while(!q.isEmpty()){
            try{
                Thread.sleep(solveSpeed);
            }
            catch(Exception e){}
            Node tmp = q.pop();
            Node node = tmp;
            if(tmp.x == xFinish && tmp.y == yFinish){
                
                Node []arr = q.getPath(node);
                cellLabel[xFinish][yFinish].setOpaque(true);
                cellLabel[tmp.x][tmp.y].setBackground(green);
                for(int i = 1;i < arr.length - 1;i++){
                    cellLabel[arr[i].x][arr[i].y].setBackground(gray);
                    try{
                        Thread.sleep(solveSpeed);
                    }
                    catch(Exception e){}
                }
                cellLabel[xStart][yStart].setIcon(null);
                cellLabel[xStart][yStart].setBackground(gray);
                for(int i = arr.length - 2;i > 0;i--){
                    cellLabel[arr[i].x][arr[i].y].setIcon(carImage);
                    try{
                        Thread.sleep(100);
                    }
                    catch(Exception e){}
                    cellLabel[arr[i].x][arr[i].y].setIcon(null);
                }
                cellLabel[tmp.x][tmp.y].setText("");
                cellLabel[tmp.x][tmp.y].setIcon(carImage);
                break;
            }
            if(expand(tmp)){
                for(int i = 0;i < 4;i++){
                    try{
                        if(node.child[i] != null){
                            cellLabel[node.child[i].x][node.child[i].y].setText(new DecimalFormat("##.##").format(node.child[i].getFn()));
                            q.push(node.child[i]);

                        }
                    }
                    catch(Exception e){}
                }
                q.print();
            }
        }
    }

    public void bfsSolve(){
        Queue q = new Queue();
        expanded = new boolean[SIZE][SIZE];
        start = new Node(xStart, yStart, 0, getEuclideanDistance(xStart, yStart));
        q.start = this.start;
        q.push(start);
        while(!q.isEmpty()){
            try{
                Thread.sleep(solveSpeed);
            }
            catch(Exception e){}
            Node tmp = q.pop();
            Node node = tmp;
            if(tmp.x == xFinish && tmp.y == yFinish){
                
                Node []arr = q.getPath(node);
                cellLabel[xFinish][yFinish].setOpaque(true);
                cellLabel[tmp.x][tmp.y].setBackground(green);
                for(int i = 1;i < arr.length - 1;i++){
                    cellLabel[arr[i].x][arr[i].y].setBackground(gray);
                    try{
                        Thread.sleep(solveSpeed);
                    }
                    catch(Exception e){}
                }
                cellLabel[xStart][yStart].setIcon(null);
                cellLabel[xStart][yStart].setBackground(gray);
                for(int i = arr.length - 2;i > 0;i--){
                    cellLabel[arr[i].x][arr[i].y].setIcon(carImage);
                    try{
                        Thread.sleep(100);
                    }
                    catch(Exception e){}
                    cellLabel[arr[i].x][arr[i].y].setIcon(null);
                }
                cellLabel[tmp.x][tmp.y].setIcon(carImage);
                break;
            }
            if(expand(tmp)){
                for(int i = 0;i < 4;i++){
                    try{
                        if(node.child[i] != null){
                            q.push(node.child[i]);

                        }
                    }
                    catch(Exception e){}
                }
                q.print();
            }
        }
    }

    public void dfsSolve(){
        Stack q = new Stack();
        expanded = new boolean[SIZE][SIZE];
        start = new Node(xStart, yStart, 0, getEuclideanDistance(xStart, yStart));
        q.start = this.start;
        q.push(start);
        while(!q.isEmpty()){
            try{
                Thread.sleep(solveSpeed);
            }
            catch(Exception e){}
            Node tmp = q.pop();
            Node node = tmp;
            if(tmp.x == xFinish && tmp.y == yFinish){
                
                Node []arr = q.getPath(node);
                cellLabel[xFinish][yFinish].setOpaque(true);
                cellLabel[tmp.x][tmp.y].setBackground(green);
                for(int i = 1;i < arr.length - 1;i++){
                    cellLabel[arr[i].x][arr[i].y].setBackground(gray);
                    try{
                        Thread.sleep(solveSpeed);
                    }
                    catch(Exception e){}
                }
                cellLabel[xStart][yStart].setIcon(null);
                cellLabel[xStart][yStart].setBackground(gray);
                for(int i = arr.length - 2;i > 0;i--){
                    cellLabel[arr[i].x][arr[i].y].setIcon(carImage);
                    try{
                        Thread.sleep(100);
                    }
                    catch(Exception e){}
                    cellLabel[arr[i].x][arr[i].y].setIcon(null);
                }
                cellLabel[tmp.x][tmp.y].setIcon(carImage);
                break;
            }
            if(expand(tmp)){
                for(int i = 0;i < 4;i++){
                    try{
                        if(node.child[i] != null){
                            q.push(node.child[i]);

                        }
                    }
                    catch(Exception e){}
                }
                q.print();
            }
        }
    }

    public boolean expand(Node node){
        boolean expandable = false;
        int x = node.x;
        int y = node.y;
        cellLabel[node.x][node.y].setOpaque(true);
        cellLabel[node.x][node.y].setBackground(white);
        expanded[x][y] = true;
        if(arrayStatus[x-1][y] == 0 && !expanded[x-1][y]){
            expandable = true;
            Node up = new Node(x - 1, y, node.level + 1, getEuclideanDistance(x-1, y));
            up.parent = node;
            cellLabel[up.x][up.y].setOpaque(true);
            cellLabel[up.x][up.y].setBackground(yellow);
            node.child[0] = up;
        }
        if(arrayStatus[x+1][y] == 0 && !expanded[x+1][y]){
            expandable = true;
            Node down = new Node(x + 1, y, node.level + 1, getEuclideanDistance(x+1, y));
            down.parent = node;
            cellLabel[down.x][down.y].setOpaque(true);
            cellLabel[down.x][down.y].setBackground(yellow);
            node.child[1] = down;                
        }
        if(arrayStatus[x][y+1] == 0 && !expanded[x][y+1]){
            expandable = true;
            Node right = new Node(x, y + 1, node.level + 1, getEuclideanDistance(x, y + 1));
            right.parent = node;
            cellLabel[right.x][right.y].setOpaque(true);
            cellLabel[right.x][right.y].setBackground(yellow);
            node.child[2] = right;
        }
        if(arrayStatus[x][y-1] == 0 && !expanded[x][y-1]){
            expandable = true;
            Node left = new Node(x, y - 1, node.level + 1, getEuclideanDistance(x, y-1));
            left.parent = node;
            cellLabel[left.x][left.y].setOpaque(true);
            cellLabel[left.x][left.y].setBackground(yellow);
            node.child[3] = left;                
        }
        return expandable;
    }


    public double getEuclideanDistance(int x, int y){
        return Math.sqrt(Math.pow((x - xFinish), 2) + Math.pow((y - yFinish), 2));
        //return Math.abs((x-xFinish) + (y - yFinish));
    }

    public void resetMaze(){
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if(arrayStatus[x][y] == 0){
                    cellLabel[x][y].setText("");
                    cellLabel[x][y].setIcon(null);
                    cellLabel[x][y].setBackground(transparent);
                    cellLabel[x][y].setOpaque(false);
                }
            }
        }
        this.xStart = SIZE - 2;
        this.yStart = 1;
        this.xFinish = 1;
        this.yFinish = SIZE - 2;
        cellLabel[xStart][yStart].setOpaque(true);
        cellLabel[xFinish][yFinish].setOpaque(true);
        cellLabel[xStart][yStart].setIcon(carImage);
        cellLabel[xFinish][yFinish].setBackground(green);
    }

    public static void main(String[] args) {
        new Thread(new threadTest()).start();
    }
    
}
