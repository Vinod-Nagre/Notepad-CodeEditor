package project1;

import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
//import javax.swing.SwingWorker;

public class CodeEditorGUI 
{
	JFrame frame;
	JTextArea textArea;
	JTextArea consoleArea; 
	JScrollPane scrollPane;
	JSplitPane splitPane;
	
	JMenuBar menuBar;
	JMenu fileMenu, editMenu, langMenu, formatMenu, cmdMenu ;
	
	JMenuItem newItem, newWindowItem, openItem, saveAsItem, saveItem, exitItem ;
	
	JMenuItem wWrapItem;
	JMenu fontItem, fontSizeItem;
	
	JMenuItem arialItem, timesNewRomanItem, consolasItem;
	
	JMenuItem iItem, iiItem, iiiItem, ivItem, vItem;
	
	JMenuItem cppItem, cItem, pythonItem, javaItem, htmlItem;
	JMenuItem cmdItem;
	
	int fontSize = 13;
	String fontStyle = "Consolas";
	boolean wrap = false;
	String openDirectory, openFileName;
	
	public CodeEditorGUI()
	{
		createFrame();
		createTextArea();
		createScrollPane();
		createMenuBar(); // Initialize menus first
		
		// Now add items to initialized menus
		createFileDropdownItem();
		createLanguageDropdownItem();
		createFormatDropdownItem();
		createCommandPromptDropdownMenu();
		
		// Set visible AFTER everything is loaded
		frame.setVisible(true);
		
	}
	
	public void createFrame()
	{
		frame = new JFrame ("Notepad Code Editor");
		
		frame.setSize(1000,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Actually closes the app
		//frame.setVisible(true);
	}
	
	public void createTextArea()
	{
		textArea = new JTextArea();
		textArea.setFont(new Font(fontStyle, Font.PLAIN, fontSize));
		//frame.add(textArea);
	}
	
	public void createScrollPane()
	{
		scrollPane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.add(scrollPane);
	}
	
    public void createMenuBar() 
    {
    	menuBar=new JMenuBar();
    	frame.setJMenuBar(menuBar);
    	
    	fileMenu = new JMenu("File");
    	editMenu = new JMenu("Edit");
    	langMenu = new JMenu("Language");
    	formatMenu = new JMenu("Format");
    	cmdMenu = new JMenu("Command Prompt");
    	
    	//createFileDropdownItem();
    	
    	menuBar.add(fileMenu);
    	menuBar.add(editMenu);
    	menuBar.add(langMenu);
    	menuBar.add(formatMenu);
    	menuBar.add(cmdMenu);  
    	
	}
    
    public void createFileDropdownItem()
    {	
    	newItem = new JMenuItem("New");
    	newItem.addActionListener(new ActionListener(){
    		@Override
    		public void actionPerformed(ActionEvent e)
    		{
    			textArea.setText("");
    		}
    	});
    	fileMenu.add(newItem);
    	
        newWindowItem = new JMenuItem("New Window");
        newWindowItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				CodeEditorGUI g2 = new CodeEditorGUI();
				g2.frame.setTitle("Untitled");
				
			}
        	
        		});
        fileMenu.add(newWindowItem);
        
        openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				FileDialog fd = new FileDialog(frame, "Open", FileDialog.LOAD);
				fd.setVisible(true);
				openDirectory = fd.getDirectory();
				openFileName = fd.getFile();
				//System.out.println(openDirectory);
				//System.out.println(openFileName);
				FileReader fr;
				
				try 
				{
					fr = new FileReader(openDirectory + openFileName);
					BufferedReader br = new BufferedReader(fr);
					textArea.setText("");
					frame.setTitle(openFileName);
					String data = br.readLine();
					while(data != null)
					{
						textArea.append(data + "\n");
						System.out.println();
						data = br.readLine();
					}
					br.close();
					fr.close();
					
				} 
				catch (FileNotFoundException e1) 
				{
					System.out.println("File path issue ! ");
				} 
				catch (IOException e1) 
				{
					System.out.println("Could not read the data.");
				}
				
			}
        	
        });
        fileMenu.add(openItem);
        
        saveAsItem = new JMenuItem("Save As");
        saveAsItem.addActionListener(new ActionListener() {

	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				saveAs();
			}
        	
        });
        fileMenu.add(saveAsItem);
        
        saveItem = new JMenuItem("Save");
        //fileMenu.add(saveItem);
        saveItem.addActionListener(new ActionListener()	{

					@Override
					public void actionPerformed(ActionEvent e) 
					{
						if(frame.getTitle().equalsIgnoreCase("Untitled") || frame.getTitle().equalsIgnoreCase("Notepad Code Editor"))
						{
							saveAs();	
						}
						else 
						{
							String path = openDirectory;
							String fileName = openFileName;
							
							frame.setTitle(fileName);
							
							String data = textArea.getText();
							
							
							try {
								FileWriter fw = new FileWriter(path + fileName);
								BufferedWriter bw = new BufferedWriter(fw);
								
								bw.write(data);
								bw.close();
								fw.close();
								
							} 
							catch (IOException e1) 
							{
								System.out.println("Issue with path");
							}
						}
								
					}
        	
        		});
        
        fileMenu.add(saveItem);
        
        
        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					frame.dispose();	
				}
        			
        }); 
        fileMenu.add(exitItem);
    }
    
    public void saveAs()
    {
    	FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);
		fd.setVisible(true);
		String path= fd.getDirectory();
		openDirectory = path;
		String fileName = fd.getFile();
		openFileName = fileName;
		
		String data = textArea.getText();

		try 
		{
			if(fileName != null)
			{
				FileWriter fw = new FileWriter(path+ fileName);
				BufferedWriter bw = new BufferedWriter(fw);
				 
				bw.write(data);
				bw.close();
				fw.close();
				 
				frame.setTitle(fileName);
			}
		} 
		catch (IOException e1) 
		{
			System.out.println("Issue in the Path!");
			
		}
    }
    
    public void createFormatDropdownItem()
    {
    	wWrapItem = new JMenuItem("Word Wrap: OFF");
    	wWrapItem.addActionListener(new ActionListener()
    			{
					@Override
					public void actionPerformed(ActionEvent e) 
					{ 
						
						if(wrap == false)
						{
							wWrapItem.setText("Word Wrap: ON");
							
							textArea.setLineWrap(true);
							wrap = true;
						}
						else 
						{
							wWrapItem.setText("Word Wrap: OFF");
							
							textArea.setLineWrap(false);
							wrap = false;
						}
					}
    		
    			});
    	formatMenu.add(wWrapItem);
    	
    	fontItem = new JMenu("Font");
    	createFontDropdownItem();
    	formatMenu.add(fontItem);
    	
    	fontSizeItem = new JMenu("Font Size");
    	createFontSizeDropdownItem();
    	formatMenu.add(fontSizeItem);
    }
    
    public void createFontDropdownItem()
    {
    	arialItem = new JMenuItem("Arial");
    	arialItem.addActionListener(new ActionListener()
    			{

					@Override
					public void actionPerformed(ActionEvent e)
					{
						setFont("Arial", fontSize);		
					}
    		
    			});
    	fontItem.add(arialItem);
    	
    	timesNewRomanItem = new JMenuItem("Times New Roman");
    	timesNewRomanItem.addActionListener(new ActionListener()
    			{

					@Override
					public void actionPerformed(ActionEvent e) 
					{
						setFont("Times New Roman", fontSize);		
					}
    		
    			});
    	fontItem.add(timesNewRomanItem);
    	
    	consolasItem = new JMenuItem("Consolas");
    	consolasItem.addActionListener(new ActionListener()
    			{

					@Override
					public void actionPerformed(ActionEvent e) 
					{
						setFont("Consolas", fontSize);	
					}
    		
    			});
    	fontItem.add(consolasItem);
    	
    }

    public void createFontSizeDropdownItem()
    {
    	int[] sizes = {10, 15, 18, 25, 29};
    	for(int s : sizes)
    	{
    		JMenuItem item = new JMenuItem(String.valueOf(s));
    		item.addActionListener(new ActionListener()
    				{

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							setFontSize(s);
						}
    			
    				});
    		fontSizeItem.add(item);	
    	}
    }
    
    public void createLanguageDropdownItem()
    {
    	String[] langs = {"C", "C++", "Java", "Python", "HTML"};
    	for(String l : langs)
    	{
    		JMenuItem item = new JMenuItem(l);
    		item.addActionListener(new ActionListener()
    				{

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							setLanguage(l);
						}
    			
    				});
    		langMenu.add(item);
    	}
    }
    
    public void createCommandPromptDropdownMenu()
    {
    	cmdItem = new JMenuItem("Open Command Prompt");
    	cmdMenu.add(cmdItem);
    	cmdItem.addActionListener(new ActionListener()
    			{

					@Override
					public void actionPerformed(ActionEvent e) 
					{
						
						try 
						{
							if(openDirectory != null)
							{
								Runtime.getRuntime().exec(new String[] {"cmd", "/K","start"}, null, new File(openDirectory));
								
							}
							else
							{
								Runtime.getRuntime().exec(new String[] {"cmd", "/K", "start"},null, null);
							}
						
						} 
						catch (IOException e1) 
						{
							System.out.println("Issue in cmd");
						}
							
					}
					
    			});
  
    }
    
    public void setFont(String fontName, int size)
    {
    	switch (fontName)
    	{
	    	case "Arial":
	    	{
	    		textArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
	    		fontStyle = "Arial";
	    		break;
	    	}
	    	case "Times New Roman":
	    	{
	    		textArea.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
	    		fontStyle = "Times New Roman";
	    		break;
	    	}
	    	case "Consolas":
	    	{
	    		textArea.setFont(new Font("Consolas", Font.PLAIN, fontSize));
	    		fontStyle = "Consolas";
	    		break;
	    	}
	    	default:
	    		break;
    	}
    }
    
    public void setFontSize(int size)
    {
    	fontSize = size;
    	setFont(fontStyle, fontSize);
    }
    
    public void setLanguage(String lang)
    {
    	String basePath = "..\\CodeEditor\\Lang Syntax\\";
    	String path = "";
    	switch (lang)
    	{
	    	case "C":
	    	{
	    		path = basePath +"c.txt";
	    		break;
	    	}
	    	case "C++":
	    	{
	    		path = basePath + "Cpp.txt";
	    		break;
	    	}
	    	case "Java":
	    	{
	    		path = basePath + "Java.txt" ;
	    		break;
	    	}
	    	case "Python":
	    	{
	    		path = basePath + "Python.txt";
	    		break;
	    	}
	    	case "HTML":
	    	{
	    		path = basePath + "Html.txt";
	    		break;
	    	}
	    	default:
	    	{
	    		break;
	    	}
    	}
    	
    	try 
    	{
    		textArea.setText("");
			//@SuppressWarnings("resource")
			BufferedReader bf = new BufferedReader(new FileReader(path));
			String line = bf.readLine();
			
			while(line != null)
			{
				textArea.append(line + "\n");
				System.out.println();
				
				line = bf.readLine();
			}
		} 
    	catch (FileNotFoundException e) 
    	{
			System.out.println("Issue with path");	
		} 
    	catch (IOException e)
    	{
			System.out.println("Issue with format" + path);
		}
    	
    }
}