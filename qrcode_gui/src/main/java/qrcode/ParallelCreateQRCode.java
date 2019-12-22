package qrcode;

import entity.Entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// 使用并行的方式创建二维码
public class ParallelCreateQRCode {
    private int width;
    private int height;
    private String format;
    private String targetDirectory;
    private boolean finished = false;

    public ParallelCreateQRCode (int width, int height, String format, String targetDirectory) {
        this.width = width;
        this.height = height;
        this.format = format;
        this.targetDirectory = targetDirectory;
    }

    public void parallelCreate(List<Entity> entityList) {
        finished = false;
        RecursiveAction mainTask = new CreateQRCodeTask(entityList);
        ForkJoinPool pool = new ForkJoinPool(3);
        pool.invoke(mainTask);
        finished = true;
    }

    private class CreateQRCodeTask extends RecursiveAction {
        private final int THRESTHOLD = 300;
        private List<Entity> entities;


        public CreateQRCodeTask(List<Entity> entities) {
            this.entities = entities;
        }

        @Override
        protected void compute() {
            if (entities.size() < THRESTHOLD) {
                // 顺序创建 qrcode
                for (Entity entity : entities) {
                    BufferedImage bufferedImage = CreateQRCode.create(entity.getContent(), width, height);
                    String newPath = targetDirectory + File.separator + entity.getName() + "." + format;
                    CreateQRCode.storeImage(bufferedImage, newPath, format);
                }
            } else {
                int size = entities.size();
                int half = size / 2 ;
                List<Entity> firstHalf = entities.subList(0, half);
                List<Entity> secondHalf = entities.subList(size - half, size);
                invokeAll(new CreateQRCodeTask(firstHalf), new CreateQRCodeTask(secondHalf));
            }
        }
    }

    public boolean getFinished () {
        return finished;
    }
}
