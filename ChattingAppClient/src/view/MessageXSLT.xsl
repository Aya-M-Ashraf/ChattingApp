<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Messages.xsl</title>
            </head>
            <body style="font-family:Arial;font-size:12pt;background-color:#EEEEEE">
                <table>
                <xsl:for-each select="MessageArea/UserSays">
                    <tr>  
                    <td>
                    <div style="background-color:teal;color:white;padding:4px">
                      <span style="font-weight:bold"><xsl:value-of select="Name"/> - </span>
                      <xsl:value-of select="Email"/>
                    </div>
                    <div style="margin-left:20px;margin-bottom:1em;font-size:10pt">
                      <p>
                      <xsl:for-each select="Message">
                        <span style="font-style:italic"><xsl:apply-templates/></span>
                        <br/>
                      </xsl:for-each>
                      </p> 
                    </div>
                    </td>
                    </tr>
                 </xsl:for-each>
                 </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
