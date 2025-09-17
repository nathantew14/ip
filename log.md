# Hair-tearing issues faced
## Checkstyle for Java VSCode extension not working
`com.puppycrawl.tools.checkstyle.api.CheckstyleException: Property ${config_loc} has not been set`
Fixed: https://github.com/jdneo/vscode-checkstyle/issues/277
Set this in the project .vscode/settings file:
```
    "java.checkstyle.properties": {
        "config_loc": "${workspaceFolder}/config/checkstyle"
    }  

```