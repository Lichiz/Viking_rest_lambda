package ru.mephi.vikingdemo.gui;

import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingLambdaService;
import ru.mephi.vikingdemo.service.VikingService;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class VikingDesktopFrame extends JFrame {

    private final VikingService vikingService;
    private final VikingTableModel tableModel = new VikingTableModel();
    private final VikingLambdaService lambdaService = new VikingLambdaService();

    public VikingDesktopFrame(VikingService vikingService) {
        this.vikingService = vikingService;

        setTitle("Viking Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 420));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Viking Demo", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));
        add(header, BorderLayout.NORTH);

        JTable vikingTable = new JTable(tableModel);
        vikingTable.setRowHeight(28);
        add(new JScrollPane(vikingTable), BorderLayout.CENTER);

        JButton createButton = new JButton("Create random viking");
        createButton.addActionListener(event -> onCreateViking());

        JButton massButton = new JButton("Create 5 viking");
        massButton.addActionListener(e -> {
            vikingService.generateAndSaveMassive(5);
            onInit();
        });

        JButton statsButton = new JButton("Statistics");
        statsButton.addActionListener(e -> showStatsDialog());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createButton);
        bottomPanel.add(massButton);
        bottomPanel.add(statsButton);
        add(bottomPanel, BorderLayout.SOUTH);

        onInit();
    }

    private void onCreateViking() {
        Viking viking = vikingService.createRandomViking();
        tableModel.addViking(viking);
    }

    private void showStatsDialog() {
        JDialog dialog = new JDialog(this, "Выбор статистики", true);
        dialog.setLayout(new GridLayout(12, 1));
        dialog.setSize(400, 500);

        ButtonGroup group = new ButtonGroup();
        JRadioButton[] buttons = {
                new JRadioButton("Возраст > 40"),
                new JRadioButton("Возраст < 20"),
                new JRadioButton("Возраст в диапазоне от 30 до 50"),
                new JRadioButton("Возраст вне диапазона от 30 до 50"),
                new JRadioButton("Длинная борода + Блондин"),
                new JRadioButton("С 1 или 2 топорами"),
                new JRadioButton("Случайный выше 180 см"),
                new JRadioButton("С легендарным вооружением"),
                new JRadioButton("Рыжебородые (сорт. по возр.)"),
                new JRadioButton("Максимальный ID в базе"),
                new JRadioButton("Все четные ID")
        };

        for (JRadioButton rb : buttons) { group.add(rb); dialog.add(rb); }
        buttons[0].setSelected(true);

        JButton confirm = new JButton("Показать результат");
        confirm.addActionListener(e -> {
            List<Viking> data = vikingService.findAll();
            Object result = "Нет данных";

            if (buttons[0].isSelected()) result = lambdaService.countByAgeGreater(data, 40);
            else if (buttons[1].isSelected()) result = lambdaService.countByAgeLess(data, 20);
            else if (buttons[3].isSelected()) result = lambdaService.countByAgeInRange(data, 30, 50);
            else if (buttons[4].isSelected()) result = lambdaService.countByAgeOutOfRange(data, 30, 50);
            else if (buttons[5].isSelected()) result = lambdaService.countByBeardAndHair(data, ru.mephi.vikingdemo.model.BeardStyle.LONG, ru.mephi.vikingdemo.model.HairColor.Blond);
            else if (buttons[6].isSelected()) result = lambdaService.countByAxes(data);
            else if (buttons[7].isSelected()) result = lambdaService.findRandomVikingTaller_180(data);
            else if (buttons[8].isSelected()) result = lambdaService.findVikingsWithLegendEquip(data);
            else if (buttons[9].isSelected()) result = lambdaService.getSortedRedBeardedVikings(data);
            else if (buttons[10].isSelected()) result = lambdaService.findMaxId(vikingService.getAllIds());
            else if (buttons[11].isSelected()) result = lambdaService.findAllEvenIds(vikingService.getAllIds());

            JOptionPane.showMessageDialog(dialog, "Результат: " + result.toString());
            dialog.dispose();
        });
        dialog.add(confirm);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void addNewViking(Viking viking){
        tableModel.addViking(viking);
    }

    private void onInit() {
        List<Viking> all = vikingService.findAll();
        if (!all.isEmpty()){
            for (Viking viking : all) {
                tableModel.addViking(viking);
            }
        }
    }
}