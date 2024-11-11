import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SymbolImage extends JFrame {
    public static String path;
    public static String[][] imageData;
//    public static Color[][] colors;

    public SymbolImage() {
        setTitle("Symbol Image");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new SymbolPanel());

        // Сохранение изображения в файл
        saveToJpg(path);
    }

    private void saveToJpg(String fileName) {
        int startX = 0; // Начальная позиция по X
        int startY = 0; // Начальная позиция по Y
        int lineHeight = 30; // Высота строки

        // Создание BufferedImage
        BufferedImage bufferedImage = new BufferedImage(210, 185, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

// Установка фона

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight()); // Заполнение фона

// Установка шрифта
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
        g2d.setColor(Color.WHITE);




// Рисует изображение на BufferedImage
        for (int index1 = 0; index1 < imageData.length; index1++) {
            String[] line = imageData[index1];
            int x = startX; // Сброс X для каждой строки
            int index2 = 0;

            for (String ch : line) {
                if (ch == null || ch.isEmpty()) {
                    continue; // Игнорировать null или пустые строки
                }

                // Проверка на пределы массива colors
//                if (index1 < colors.length && index2 < colors[index1].length) {
//                    g2d.setColor(colors[index1][index2]);
//                } else {
//                    g2d.setColor(Color.BLACK); // Установить черный цвет, если нет заданного
//                }

                g2d.drawString(ch, x, (startY + lineHeight * index1)/4);
                x += 6; // Увеличение X для следующего символа
                index2++;
            }
        }

// Очистка ресурсов


        g2d.dispose(); // Освобождение ресурсов

        // Сохранение изображения в файл
        try {
            ImageIO.write(bufferedImage, "jpg", new File(fileName));
            System.out.println("Изображение сохранено в файл: " + fileName);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении изображения: " + e.getMessage());
        }
    }

    private class SymbolPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawImage(g);
        }

        private void drawImage(Graphics g) {
            int x = 0; // Начальная позиция по X
            int y = 0; // Начальная позиция по Y
            g.setFont(new Font("Monospaced", Font.PLAIN, 5));

            int index1 = 0;
            int index2 = 0;

            // Рисует изображение на BufferedImage
            for (String[] line : imageData) {
                for (String ch : line) {
                    if (ch == null) {
                        break;
                    }
//                    g.setColor(colors[index1][index2]);
                    g.drawString(ch, x, y);
                    x += 20;
                    index2++;
                }
                y += 20;
                x = 0;
                index1++;
                index2 = 0;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 26; i++) {
            String asciiChars = "@%#*+=-:. ";
            //String asciiChars = "$% ";
//        String asciiChars = "█GMei=- ";
            boolean inverted = true;
            int buffer = 1;
            String pathToFile = "C:\\Users\\Nick\\IdeaProjects\\cgTask2\\kolobki\\dbddc54cd2fc42108923d4cf619df8c3S8M7zWjXD6FRM6Iv-" + i + ".jpg";

            path = "C:\\Users\\Nick\\IdeaProjects\\cgTask2\\output\\kolobok_" + i + ".jpg";

            AsciiGen asciiGen = new AsciiGen(asciiChars, inverted, buffer, pathToFile);

            imageData = asciiGen.makeArtMonochromic();
//        colors = asciiGen.getRGBs();

            SwingUtilities.invokeLater(() -> {
                SymbolImage ex = new SymbolImage();
                ex.setVisible(true);
            });
        }
    }
}