<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>
                    <xsl:value-of select="MessageArea/@sender" /> <!--AND <xsl:value-of select="MessageArea/@reciever" />-->
                </title>
            </head>
            <body style="background: linear-gradient(to bottom , rgba(240,209,197,1) 0%, rgba(255,214,186,1) 26%, rgba(218,255,176,1) 50%, rgba(202,235,218,1) 67%, rgba(204,232,232,1) 78%, rgba(222,218,245,1) 89%, rgba(222,218,245,1) 100%);">
                <font face="Comic sans MS" color="#0a6a85">
                <center>
                    <h3 >
                        <xsl:value-of select="MessageArea/@sender" /> <br/>and<br/><xsl:value-of select="MessageArea/@reciever" />
                    </h3>
                </center>
                <table align="center" style="border-radius:6px;border: 2px solid #0a6a85;">
                <xsl:for-each select="MessageArea/UserSays">
                    <tr>  
                    <th>
                        <p>
                           <font face="Comic sans MS" color="#0a6a85"> <xsl:value-of select="Email"/></font>
                        </p> 
                    </th>
                    <td></td>
                    <td id="msg" style="border: 2px solid #0a6a85;padding: 10px 10px 10px 10px;background:transparent;border-radius:30px;">
                        <p>
                      <xsl:for-each select="Message">
                        <xsl:apply-templates/>
                        <br/>
                      </xsl:for-each>
                        </p>
                    </td>
                    </tr>
                 </xsl:for-each>
                 </table>
                </font>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
