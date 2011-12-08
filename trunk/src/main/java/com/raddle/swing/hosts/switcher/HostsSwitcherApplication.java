package com.raddle.swing.hosts.switcher;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.commons.lang.StringUtils;

import com.raddle.swing.hosts.switcher.dao.Db4oDao;
import com.raddle.swing.hosts.switcher.manager.HostsManager;
import com.raddle.swing.hosts.switcher.model.Hosts;
import com.raddle.swing.hosts.switcher.pane.HostPane;
import com.raddle.swing.hosts.switcher.sort.HostsComparator;

public class HostsSwitcherApplication {

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
    }

    private JFrame jFrame = null;
    private JPanel jContentPane = null;
    private JMenuBar jJMenuBar = null;
    private JMenu fileMenu = null;
    private JMenu helpMenu = null;
    private JMenuItem exitMenuItem = null;
    private JMenuItem aboutMenuItem = null;
    private JMenuItem addHostsItem = null;
    private JDialog aboutDialog = null;
    private JPanel aboutContentPane = null;
    private JLabel aboutVersionLabel = null;
    private JTabbedPane tabbedPane;
    private HostsManager hostsManager = new HostsManager();

    /**
     * This method initializes jFrame
     * 
     * @return javax.swing.JFrame
     */
    private JFrame getJFrame() {
        if (jFrame == null) {
            jFrame = new JFrame();
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setJMenuBar(getJJMenuBar());
            jFrame.setSize(796, 572);
            jFrame.setContentPane(getJContentPane());
            jFrame.setTitle("Hosts 管理器");
            // 居中显示
            double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            jFrame.setLocation((int) (width - jFrame.getWidth()) / 2, (int) (height - jFrame.getHeight()) / 2);
            // 初始化
            List<Hosts> allHosts = hostsManager.getAllHosts();
            Collections.sort(allHosts, new HostsComparator());
            for (Hosts hosts : allHosts) {
                HostPane hostPane = new HostPane();
                hostPane.setHosts(hosts);
                hostPane.init();
                tabbedPane.add(hosts.getEnv(), hostPane);
            }
        }
        return jFrame;
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout(0, 0));
            jContentPane.add(getTabbedPane());
        }
        return jContentPane;
    }

    /**
     * This method initializes jJMenuBar
     * 
     * @return javax.swing.JMenuBar
     */
    private JMenuBar getJJMenuBar() {
        if (jJMenuBar == null) {
            jJMenuBar = new JMenuBar();
            jJMenuBar.add(getFileMenu());
            jJMenuBar.add(getHelpMenu());
        }
        return jJMenuBar;
    }

    /**
     * This method initializes jMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getFileMenu() {
        if (fileMenu == null) {
            fileMenu = new JMenu();
            fileMenu.setText("操作");
            fileMenu.add(getAddHostsItem());
            fileMenu.add(getExitMenuItem());
        }
        return fileMenu;
    }

    /**
     * This method initializes jMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getHelpMenu() {
        if (helpMenu == null) {
            helpMenu = new JMenu();
            helpMenu.setText("Help");
            helpMenu.add(getAboutMenuItem());
        }
        return helpMenu;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getExitMenuItem() {
        if (exitMenuItem == null) {
            exitMenuItem = new JMenuItem();
            exitMenuItem.setText("Exit");
            exitMenuItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }
        return exitMenuItem;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getAboutMenuItem() {
        if (aboutMenuItem == null) {
            aboutMenuItem = new JMenuItem();
            aboutMenuItem.setText("About");
            aboutMenuItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog aboutDialog = getAboutDialog();
                    aboutDialog.pack();
                    Point loc = getJFrame().getLocation();
                    loc.translate(20, 20);
                    aboutDialog.setLocation(loc);
                    aboutDialog.setVisible(true);
                }
            });
        }
        return aboutMenuItem;
    }

    /**
     * This method initializes aboutDialog
     * 
     * @return javax.swing.JDialog
     */
    private JDialog getAboutDialog() {
        if (aboutDialog == null) {
            aboutDialog = new JDialog(getJFrame(), true);
            aboutDialog.setTitle("About");
            aboutDialog.setContentPane(getAboutContentPane());
        }
        return aboutDialog;
    }

    /**
     * This method initializes aboutContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getAboutContentPane() {
        if (aboutContentPane == null) {
            aboutContentPane = new JPanel();
            aboutContentPane.setLayout(new BorderLayout());
            aboutContentPane.add(getAboutVersionLabel(), BorderLayout.CENTER);
        }
        return aboutContentPane;
    }

    /**
     * This method initializes aboutVersionLabel
     * 
     * @return javax.swing.JLabel
     */
    private JLabel getAboutVersionLabel() {
        if (aboutVersionLabel == null) {
            aboutVersionLabel = new JLabel();
            aboutVersionLabel.setText("Version 1.0");
            aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return aboutVersionLabel;
    }

    /**
     * This method initializes jMenuItem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getAddHostsItem() {
        if (addHostsItem == null) {
            addHostsItem = new JMenuItem();
            addHostsItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String input = JOptionPane.showInputDialog("请输入Hosts名称");
                    if (StringUtils.isNotBlank(input)) {
                        Hosts hosts = new Hosts();
                        hosts.setEnv(input);
                        hosts.setId(UUID.randomUUID().toString());
                        hostsManager.saveHosts(hosts);
                        HostPane hostPane = new HostPane();
                        hostPane.setHosts(hosts);
                        hostPane.init();
                        tabbedPane.add(hosts.getEnv(), hostPane);
                    }
                }
            });
            addHostsItem.setText("新增Hosts");
            addHostsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UNDEFINED, 0));
        }
        return addHostsItem;
    }

    /**
     * Launches this application
     */
    public static void main(String[] args) {
        Db4oDao.init();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                HostsSwitcherApplication application = new HostsSwitcherApplication();
                application.getJFrame().setVisible(true);

            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                Db4oDao.close();
            }
        });
    }

    private JTabbedPane getTabbedPane() {
        if (tabbedPane == null) {
            tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        }
        return tabbedPane;
    }
}
