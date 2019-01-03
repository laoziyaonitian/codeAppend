package org.codeg.intellij.builder;

import org.codeg.intellij.config.Cache;
import org.codeg.intellij.config.Constants;
import org.codeg.intellij.entity.ClassEntity;
import org.codeg.intellij.entity.FieldEntity;
import org.codeg.intellij.util.FileUtils;
import org.codeg.intellij.util.RegexUtils;
import org.codeg.intellij.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * mapper构造器
 * @author liufei
 * @date 2018/12/28 11:17
 */
public class MapperBuilder {

    public static void buildMapperFile(ClassEntity classEntity, List<FieldEntity> fieldEntities) {
        // 查看目录是否为空
        String mapperPath = Cache.getInstant().getMapperPath();
        if (StringUtils.isNotBlank(mapperPath)) {
            // result处理
            StringBuilder result = new StringBuilder();
            StringBuilder cloumns = new StringBuilder();
            for (FieldEntity fieldEntity : fieldEntities) {
                result.append(Constants.mapperResultStr.replaceAll("\\{column}", fieldEntity.getColumn())
                        .replaceAll("\\{property}",fieldEntity.getProperty()));
                cloumns.append(Constants.mapperColumnsStr.replaceAll("\\{column}", fieldEntity.getColumn()));
            }
            // 去除逗号
            String columnsStr = cloumns.toString();
            columnsStr = columnsStr.substring(0, columnsStr.length() - 1);
            // mapper文件处理
            String content = Constants.mapperStr.replaceAll("\\{daoPackage}", Cache.getInstant().getDaoPackage()).
                    replaceAll("\\{entityPackage}", Cache.getInstant().getEntityPackage()).
                    replaceAll("\\{resultFields}", result.toString()).
                    replaceAll("\\{columns}", columnsStr).
                    replaceAll("\\{className}", classEntity.getClassName());
            final String javaFilePath = StringUtils.getMapperFilePath(Cache.getInstant().getMapperPath(), classEntity.getClassName());
            FileUtils.createFile(javaFilePath, content);
        }
    }

    public static void appendMapperFile(ClassEntity classEntity, List<FieldEntity> fieldEntities) {
        // 获取实体文件
        final String javaFilePath = StringUtils.getMapperFilePath(Cache.getInstant().getMapperPath(), classEntity.getClassName());
        // 获取内容
        final String originContent = FileUtils.readContent(javaFilePath);
        // 获取追加标志
        String replaceResultStr = RegexUtils.parseResultMapReplaceStr(originContent);
        String replaceFieldsStr = RegexUtils.parseResultFieldsReplaceStr(originContent);
        if (Objects.isNull(originContent) || StringUtils.isBlank(replaceResultStr)) {
            return;
        }
        // 设置替换标志
        String replaceContent = originContent.replaceFirst(replaceResultStr, replaceResultStr + "\n" + "{appendResultFields}")
                                .replaceFirst(replaceFieldsStr,"\n\t\t{appendColumns}\n\t");
        // 处理追加内容
        StringBuilder appendResultFields = new StringBuilder();
        StringBuilder appendColumns = new StringBuilder();
        for (FieldEntity fieldEntity : fieldEntities) {
            final String column = fieldEntity.getColumn();
            if (!originContent.contains(StringUtils.mapperColumn(column))) {
                appendResultFields.append(Constants.mapperResultStr.replaceAll("\\{column}", fieldEntity.getColumn())
                        .replaceAll("\\{property}",fieldEntity.getProperty()));
            }
            appendColumns.append(Constants.mapperColumnsStr.replaceAll("\\{column}", fieldEntity.getColumn()));
        }
        // 去除逗号
        String columnsStr = appendColumns.toString();
        columnsStr = columnsStr.substring(0, columnsStr.length() - 1);
        replaceContent = replaceContent.replace("{appendResultFields}", appendResultFields.toString())
                        .replace("{appendColumns}",columnsStr);
        FileUtils.createFile(javaFilePath, replaceContent);
    }
}