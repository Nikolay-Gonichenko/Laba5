import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 * @author Gonichenko Nikolay R3136
 * This is main class
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * This is start point of program
     *
     * @param args
     */
    public static void main(String[] args) {
        printOut();
        MyCollection collection = new MyCollection();
        String fileName = args[0];
        boolean checkFileIsRead = false;
        try {
            collection.fillFromFile(fileName);
            checkFileIsRead = true;
        } catch (FileNotFoundException e) {
            System.out.println("This file doesn't exist. Enter file from console");
        } catch (IOException e) {
            System.out.println("Something went wrong with reading data from file. Enter file from console");
        }
        if (!checkFileIsRead) {
            while (true) {
                System.out.println("Enter a file's name for starting");
                fileName = scanner.nextLine();
                try {
                    collection.fillFromFile(fileName);
                    break;
                } catch (IOException e) {
                    System.out.println("Something went wrong with reading file");
                }
            }
        }

        System.out.println("Enter a command");
        String command = scanner.nextLine();
        String[] commands = command.split(" ");
        while (!commands[0].equals("exit")) {
            switch (commands[0]) {
                case "help":
                    printOut();
                    break;
                case "info":
                    collection.info();
                    break;
                case "show":
                    if (!collection.checkToEmpty()) {
                        collection.show();
                    } else {
                        System.out.println("Collection is empty");
                    }
                    break;
                case "add":
                    System.out.println("Please enter an element");
                    Vehicle vehicle = MyReader.getElementFromConsole(scanner);
                    collection.add(vehicle);
                    break;
                case "update":
                    System.out.println("Please enter an id of element ");
                    String id_check;
                    int id;
                    while (true) {
                        id_check = scanner.nextLine();
                        try {
                            id = Integer.parseInt(id_check);
                            if (id > 0) {
                                boolean check_in_queue = false;
                                for (Vehicle v : collection.getQueue()) {
                                    if (v.getId() == id) {
                                        check_in_queue = true;
                                        break;
                                    }
                                }
                                if (check_in_queue) {
                                    break;
                                } else throw new IllegalArgumentException();
                            } else {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Id has to be int and > 0. Try again");
                        } catch (IllegalArgumentException e) {
                            System.out.println("You entered id of element which doesn't exist.Try again");
                        }
                    }
                    Vehicle vehicleUpdate = MyReader.getElementFromConsoleToUpdate(scanner, id);
                    collection.update(id, vehicleUpdate);
                    break;
                case "remove_by_id":
                    if (!collection.checkToEmpty()) {
                        String idToRemove_check;
                        int count_check = 1;
                        int idToRemove;
                        while (true) {
                            try {
                                if (count_check == 1) {
                                    idToRemove_check = commands[1];
                                } else {
                                    System.out.println("Please enter an id of element ");
                                    idToRemove_check = scanner.nextLine();
                                }
                                idToRemove = Integer.parseInt(idToRemove_check);
                                if (idToRemove > 0) {
                                    boolean check_in_queue = false;
                                    for (Vehicle v : collection.getQueue()) {
                                        if (v.getId() == idToRemove) {
                                            check_in_queue = true;
                                            break;
                                        }
                                    }
                                    if (check_in_queue) {
                                        break;
                                    } else {
                                        count_check++;
                                        throw new IllegalArgumentException();
                                    }
                                } else {
                                    count_check++;
                                    throw new NumberFormatException();
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Id has to be int and > 0. Try again");
                            } catch (IllegalArgumentException e) {
                                System.out.println("You entered id of element which doesn't exist.Try again");
                            } catch (IndexOutOfBoundsException e) {
                                count_check++;
                                System.out.println("You didn't enter an id. Do it");
                            }
                        }
                        collection.removeById(idToRemove);
                    } else {
                        System.out.println("Collection is empty");
                    }
                    break;
                case "clear":
                    collection.clear();
                    break;
                case "save":
                    try {
                        collection.save("Output.xml");
                        System.out.println("Data is saved to Output.xml");
                    } catch (IOException e) {
                        System.out.println("File doesn't exist. Enter a command");
                    }
                    break;
                case "execute_script":
                    String scriptName;
                    int count_check1 = 1;
                    while (true) {
                        try {
                            if (count_check1 == 1) {
                                scriptName = commands[1];
                            } else {
                                System.out.println("Enter a scriptName");
                                scriptName = scanner.nextLine();
                            }
                            MyReader.readScript(scriptName, fileName, collection);
                            break;
                        } catch (FileNotFoundException e) {
                            System.out.println("File doesn't exist. Enter command and scriptName again");
                            count_check1++;
                        } catch (IOException | IllegalArgumentException e) {
                            count_check1++;
                            System.out.println("Command in file incorrect. Executing of file has stopped.");
                        } catch (IndexOutOfBoundsException e) {
                            count_check1++;
                            System.out.println("You didn't enter a scriptName. Do it");
                        }
                    }
                    break;
                case "remove_first":
                    if (!collection.checkToEmpty()) {
                        collection.removeFirst();
                    } else {
                        System.out.println("Collection is empty. Add element firstly");
                    }
                    break;
                case "remove_head":
                    if (!collection.checkToEmpty()) {
                        System.out.println(collection.showFirst());
                    } else {
                        System.out.println("Collection is empty");
                    }
                    break;
                case "add_if_max":
                    System.out.println("Please enter an id of element");
                    String idCheck;
                    int idAddIfMax;
                    while (true) {
                        idCheck = scanner.nextLine();
                        try {
                            idAddIfMax = Integer.parseInt(idCheck);
                            if (idAddIfMax > 0) {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Id has to be int and > 0");
                        }
                    }
                    Vehicle vehicleAddIfMax = MyReader.getElementFromConsoleToUpdate(scanner, idAddIfMax);
                    collection.addIfMax(vehicleAddIfMax);
                    break;
                case "remove_any_by_fuel_type":
                    if (!collection.checkToEmpty()) {
                        String fuelType;
                        int count_check2 = 1;
                        while (true) {
                            try {
                                if (count_check2 == 1) {
                                    fuelType = commands[1];
                                } else {
                                    System.out.println("Enter a FuelType");
                                    fuelType = scanner.nextLine();
                                }
                                collection.removeByFuelType(fuelType);
                                break;
                            } catch (IllegalArgumentException e) {
                                count_check2++;
                                System.out.println("This FuelType doesn't exist. Try again");
                            } catch (IndexOutOfBoundsException e) {
                                count_check2++;
                                System.out.println("You didn't enter a fuelType. Do it");
                            }
                        }
                    } else {
                        System.out.println("Collection is empty. Add element firstly.");
                    }
                    break;
                case "max_by_name":
                    if (!collection.checkToEmpty()) {
                        System.out.println(collection.getMaxName());
                    } else {
                        System.out.println("Collection is empty");
                    }
                    break;
                case "group_counting_by_creation_date":
                    if (!collection.checkToEmpty()) {
                        Map<LocalDateTime, Integer> LocalDateMap = collection.groupByCreationDate();
                        Set<LocalDateTime> keys = LocalDateMap.keySet();
                        for (LocalDateTime key : keys) {
                            System.out.println("Creation date is " + key + " .The amounts is " + LocalDateMap.get(key));
                        }
                    } else {
                        System.out.println("Collection is empty.Add elements firstly");
                    }
                    break;
                default:
                    System.out.println("There is no this command. Try again");
            }
            System.out.println("Enter a command");
            command = scanner.nextLine();
            commands = command.split(" ");
        }
    }

    /**
     * Method prints the legend
     */
    public static void printOut() {
        System.out.println("All commands: \n"
                + "help - show list of commands \n"
                + "info - show information about collection \n"
                + "show - show all elements of collection \n"
                + "add - add new element \n"
                + "update id - update element with id;you need to have the id in the same line as command\n"
                + "remove_by_id id - remove element from collection; you have to write the id in the same line as command\n"
                + "clear - clear the collection \n"
                + "save - save collection to file \n"
                + "execute_script scriptName - read and execute commands from file_name; you have to write the scriptName in the same line as command \n"
                + "exit - end the program \n"
                + "remove_first - remove first element \n"
                + "remove_head - remove fist element and delete him \n"
                + "add_if_max - add element if it's bigger than max element in collection \n"
                + "remove_any_by_fuel_type fuelType - remove elements which have this fuelType; you have to write the fuelType in the same line as command \n"
                + "max_by_name - show element with max name \n"
                + "group_counting_by_creation_date - group elements by creationDate and show amounts of each group \n");
    }
}
