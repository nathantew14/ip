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
## Messages VBox won't grow
Confirmed with styling that the `ScrollPane` does resize, but everything inside refuses to.
Tried everything imagineable:
```
maxWidth="-Infinity"
VBox.vgrow="ALWAYS"
AnchorPane.topAnchor="0.0"
AnchorPane.leftAnchor="0.0"
AnchorPane.rightAnchor="0.0"
AnchorPane.bottomAnchor="41.0"
```
Only thing that changed the width was hard-setting a `minWidth` for `VBox` or `DialogBox`.
