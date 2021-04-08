/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:40:27
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-08 01:22:52
 */
package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BGameSettings {
    private Map<String, Object> upgradeConfigMap;
    private Map<ResourceType, Map<Integer, Map<ResourceType, Integer>>> upgradeResourceRequirementsConfigMap;
    private Map<ResourceType, Map</* level */Integer, /* category */
            Map</* categoryType */ResourceType, Map</* requiredBuildingType */ResourceType, /* requiredBuildingLevel */Integer>>>> upgradeBuildingRequirementsConfigMap;

    private BGameSettings() {
        if ((upgradeConfigMap = openUpgradeConfigMap(saveName)) == null) {
            loadDefaultConfiguration();
            saveToJsonFile(saveName, upgradeConfigMap);
        }
    }

    private void initUpgradeBuildingRequirementsConfigMap() {
        upgradeBuildingRequirementsConfigMap = new HashMap<>();
        upgradeBuildingRequirementsConfigMap.put(ResourceType.STORAGE, new HashMap<>() {
            private static final long serialVersionUID = 1L;
            {/* for level x */
                put(1, new HashMap<>() {
                    private static final long serialVersionUID = 1L;
                    {/* category */
                        put(ResourceType.MATPRODUCER, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {/* building type & level requirements */
                                put(ResourceType.BUILDMAT1, 1);
                                put(ResourceType.BUILDMAT2, 1);
                                put(ResourceType.BUILDMAT3, 1);
                            }
                        });
                    }
                });
            }
        });
    }

    private void loadDefaultConfiguration() {
        initUpgradeBuildingRequirementsConfigMap();
        initUpgradeResourceRequirementsConfigMap();
        upgradeConfigMap = new HashMap<>();
        upgradeConfigMap.put(upgradeResourceRequirementsConfigMapString, upgradeResourceRequirementsConfigMap);
        upgradeConfigMap.put(upgradeBuildingRequirementsConfigMapString, upgradeBuildingRequirementsConfigMap);

    }

    private void initUpgradeResourceRequirementsConfigMap() {
        upgradeResourceRequirementsConfigMap = new HashMap<>();
        /* #region REQUIREMENTS MAP DEFAULTS */
        upgradeResourceRequirementsConfigMap.put(ResourceType.CAPITAL,
                new HashMap<Integer, Map<ResourceType, Integer>>() {
                    private static final long serialVersionUID = 1L;
                    {
                        put(1, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(2, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(3, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(4, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(5, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(6, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                    }
                });
        upgradeResourceRequirementsConfigMap.put(ResourceType.BUILDMAT1,
                new HashMap<Integer, Map<ResourceType, Integer>>() {
                    private static final long serialVersionUID = 1L;
                    {
                        put(1, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 1200);
                                put(ResourceType.BUILDMAT2, 400);
                                put(ResourceType.BUILDMAT3, 400);
                            }
                        });
                        put(2, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(3, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(4, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(5, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(6, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                    }
                });
        upgradeResourceRequirementsConfigMap.put(ResourceType.BUILDMAT2,
                new HashMap<Integer, Map<ResourceType, Integer>>() {
                    private static final long serialVersionUID = 1L;
                    {
                        put(1, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 400);
                                put(ResourceType.BUILDMAT2, 1200);
                                put(ResourceType.BUILDMAT3, 400);
                            }
                        });
                        put(2, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(3, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(4, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(5, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(6, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                    }
                });
        upgradeResourceRequirementsConfigMap.put(ResourceType.BUILDMAT3,
                new HashMap<Integer, Map<ResourceType, Integer>>() {
                    private static final long serialVersionUID = 1L;
                    {
                        put(1, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 400);
                                put(ResourceType.BUILDMAT2, 400);
                                put(ResourceType.BUILDMAT3, 1200);
                            }
                        });
                        put(2, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(3, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(4, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(5, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(6, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                    }
                });
        upgradeResourceRequirementsConfigMap.put(ResourceType.STORAGE,
                new HashMap<Integer, Map<ResourceType, Integer>>() {
                    private static final long serialVersionUID = 1L;
                    {
                        put(1, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 2000);
                                put(ResourceType.BUILDMAT2, 2000);
                                put(ResourceType.BUILDMAT3, 2000);
                            }
                        });
                        put(2, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(3, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(4, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(5, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                        put(6, new HashMap<>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(ResourceType.BUILDMAT1, 8000);
                                put(ResourceType.BUILDMAT2, 8000);
                                put(ResourceType.BUILDMAT3, 8000);
                            }
                        });
                    }
                });
        /* #endregion */
    }

    private static final String BGAMEFOLDERNAME = "bgame-config";
    private final String saveName = "bgame.config.json";
    private final String upgradeResourceRequirementsConfigMapString = "upgradeResourceRequirementsConfigMap";
    private final String upgradeBuildingRequirementsConfigMapString = "upgradeBuildingRequirementsConfigMap";

    public static String open(String filename){
        StringBuilder stringBuilder = new StringBuilder();
        Path fullPath=Paths.get(BGAMEFOLDERNAME,filename);
        if (Files.exists(fullPath))
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fullPath.toString()))) { 
            String lineRead;
            while ((lineRead = bufferedReader.readLine()) != null) {
                stringBuilder.append(lineRead); 
                stringBuilder.append(System.lineSeparator());
            } 
        } catch (IOException io) {
            io.printStackTrace();
        }
        return stringBuilder.toString();
    }
    private Map<String, Object> openUpgradeConfigMap(String upgradeConfigMapSaveName) {  
            try {
                String result =open(upgradeConfigMapSaveName);
                if (!result.isEmpty() && !result.isBlank()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    JsonNode jsonNode = objectMapper.readTree(result);
                    JsonNode upgradeResourceRequirementsConfigMapNode = jsonNode
                            .get(this.upgradeResourceRequirementsConfigMapString);
                    JsonNode upgradeBuildingRequirementsConfigMapNode = jsonNode
                            .get(this.upgradeBuildingRequirementsConfigMapString);
                    if (upgradeBuildingRequirementsConfigMapNode != null
                            && upgradeResourceRequirementsConfigMapNode != null) {
                        String tmpRes = upgradeResourceRequirementsConfigMapNode.toString();
                        String tmpBui = upgradeBuildingRequirementsConfigMapNode.toString();
                        setUpgradeResourceRequirementsConfigMap(objectMapper.readValue(tmpRes,
                                new TypeReference<Map<ResourceType, Map<Integer, Map<ResourceType, Integer>>>>() {
                                }));
                        setUpgradeBuildingRequirementsConfigMap(objectMapper.readValue(tmpBui,
                                new TypeReference<Map<ResourceType, Map<Integer, Map<ResourceType, Map<ResourceType, Integer>>>>>() {
                                }));
                        Map<String, Object> upgradeConfigMap = new HashMap<>();
                        upgradeConfigMap.put(getUpgradeResourceRequirementsConfigMapString(),
                                getUpgradeResourceRequirementsConfigMap());
                        upgradeConfigMap.put(getUpgradeBuildingRequirementsConfigMapString(),
                                getUpgradeBuildingRequirementsConfigMap());
                        return upgradeConfigMap;
                    }

                }
            } catch (IOException io) {
                io.printStackTrace();
            }
        return null;
    }
    public static void save(String saveName, String saveData){
        Path bgameFolderPath=Paths.get(BGAMEFOLDERNAME);
        if (!Files.isDirectory(bgameFolderPath)){
            try {
                Files.createDirectories(bgameFolderPath);
            } catch (IOException e) { 
                e.printStackTrace();
                return;
            }
        }
        Path fullSavePath=Paths.get(BGAMEFOLDERNAME,saveName);
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fullSavePath.toString()))) { 
            printWriter.write(saveData);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    private void saveToJsonFile(String saveName, Map<String, Object> upgradeConfigMap) { 
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writeValueAsString(upgradeConfigMap); 
            save(saveName, result);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private static class HoldInstance {
        private static final BGameSettings INSTANCE = new BGameSettings();
    }

    public static BGameSettings getInstance() {
        return HoldInstance.INSTANCE;
    }

    public static Map<Integer, Map<ResourceType, Integer>> getUpgradeResourceRequirementsMapFor(
            ResourceType resourceType) {
        return getInstance().upgradeResourceRequirementsConfigMap.get(resourceType);
    }

    public static Map<Integer, Map<ResourceType, Map<ResourceType, Integer>>> getUpgradeBuildingRequirementsMapFor(
            ResourceType resourceType) {
        return getInstance().upgradeBuildingRequirementsConfigMap.get(resourceType);
    }

    private Map<ResourceType, Map<Integer, Map<ResourceType, Integer>>> getUpgradeResourceRequirementsConfigMap() {
        return upgradeResourceRequirementsConfigMap;
    }

    private void setUpgradeResourceRequirementsConfigMap(
            Map<ResourceType, Map<Integer, Map<ResourceType, Integer>>> upgradeResourceRequirementsConfigMap) {
        this.upgradeResourceRequirementsConfigMap = upgradeResourceRequirementsConfigMap;
    }

    private Map<ResourceType, Map<Integer, Map<ResourceType, Map<ResourceType, Integer>>>> getUpgradeBuildingRequirementsConfigMap() {
        return upgradeBuildingRequirementsConfigMap;
    }

    private void setUpgradeBuildingRequirementsConfigMap(
            Map<ResourceType, Map<Integer, Map<ResourceType, Map<ResourceType, Integer>>>> upgradeBuildingRequirementsConfigMap) {
        this.upgradeBuildingRequirementsConfigMap = upgradeBuildingRequirementsConfigMap;
    }

    private String getUpgradeResourceRequirementsConfigMapString() {
        return upgradeResourceRequirementsConfigMapString;
    }

    private String getUpgradeBuildingRequirementsConfigMapString() {
        return upgradeBuildingRequirementsConfigMapString;
    }

}