package ru.lushenko.fitnesscentr.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Action {

    /***
     * Метод отображает отображает подробную информацию имеющихся абонементов
     * @param repository - список абонементов*/
    public static void showAllSubscription(Repository<String, TypeSubscription> repository){
        for (TypeSubscription typeSubscription : repository.getAll())
            typeSubscription.printDescriptorSubscription();
    }

    /***
     * Метод для выбора абонемента при покупке
     * @param repository - список абонементов*/
    public static void selectSubscriptionForBuy(Repository<String,TypeSubscription> repository){
        for (TypeSubscription typeSubscription : repository.getAll()) {
            System.out.println("[" + typeSubscription.getId() + "] " + typeSubscription.getName());
        }
        int a = repository.getAll().size() + 1;
        System.out.println("[" + a + "] Назад" );
    }

    /***
     * Метод для проверки ID покупки
     * @param id - ID покупки
     * @param repository - репозиторий с покупками
     */
    public static void checkBuyID(String id, Repository<String, Buy> repository) {
        boolean checkStatus = false;
        for (Buy buy : repository.getAll()){
            if (buy.getId().equals(id)){
                //Считываем наименование абонемента по найденному ID
                System.out.println("Ваш абонемент " + buy.getBuyName());
                checkStatus = true;
                break;
            }
        }
        if (checkStatus == false){
            System.out.println("По данному ID покупка не найдена");
        }
    }

    /*Генерируем ID покупки*/
    public static String generationRandomId(int length) {
        String mCHAR = "0123456789";
        int strLength = length;
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strLength; i++) {
            int number = random.nextInt(mCHAR.length());
            char ch = mCHAR.charAt(number);
            builder.append(ch);
        }
        return builder.toString();
    }

    /***
     * Метод для выполнения покупки
     * @param repository - список абонементов
     * @param buyRepository - репозиторий для записи покупок
     */
    public static void buySubscription(Repository<String, TypeSubscription> repository, Repository<String, Buy> buyRepository) {
        //Вводим позицию абонемента, который хотим купить
        String position = getPrintInput();
        boolean doneBuy = false;
        for (TypeSubscription typeSubscription : repository.getAll()) {
            int kol = repository.getAll().size() + 1;
            String numberForExit = "" + kol;
            if (position.equals(numberForExit)){
                doneBuy = true;
                break;
            }
            else if (typeSubscription.getId().equals(position)) {
                Buy buy = new Buy(typeSubscription.getName(), Action.generationRandomId(5));
                /*Выполняем запись покупки*/
                buyRepository.add(buy);
                /*Отображение ID покупки*/
                System.out.println("Вы выбрали абонемент " + typeSubscription.getName() + ", ID покупки: " + buy.getId());
                doneBuy = true;
                break;
            }
        }
        if (doneBuy == false)
            System.out.println("Введенное значение отсутствует в списке. Введите корректное значение.");
    }

    /*Метод возвращает введенный текст*/
    public static String getPrintInput() {
        String valuePrint = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return valuePrint = reader.readLine();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}