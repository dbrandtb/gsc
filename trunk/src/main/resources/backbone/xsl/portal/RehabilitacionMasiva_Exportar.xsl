<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
		<item-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			
			<xsl:if test="@ASEGURADO">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@ASEGURADO"/>
				</string>
			</xsl:if>
			
			<!-- xsl:if test="@ASEG">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@ASEG"/>
				</string>
			</xsl:if-->		
		    
		    <xsl:if test="@ASEGURADORA">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@ASEGURADORA"/>
				</string>
		    </xsl:if>	
		    	
		    <!--xsl:if test="@PROD">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@PROD"/>
				</string>
		    </xsl:if-->	
		    	
		    <xsl:if test="@PRODUCTO">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@PRODUCTO"/>
				</string>
		    </xsl:if>	
		    	
		    <xsl:if test="@POLIZA">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@POLIZA"/>
				</string>
		    </xsl:if>	
		    	
		    <xsl:if test="@INCISO">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@INCISO"/>
				</string>
		    </xsl:if>	
		    	
		    <xsl:if test="@FECANCEL">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@FECANCEL"/>
				</string>
		    </xsl:if>	
		    	
		    <!--xsl:if test="@CDRAZON">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@CDRAZON"/>
				</string>
		    </xsl:if-->	
		    	
		    <xsl:if test="@DSRAZON">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@DSRAZON"/>
				</string>
		    </xsl:if>	
		    	
		    <xsl:if test="@COMENTARIOS">
				<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@COMENTARIOS"/>
				</string>
		    </xsl:if>	
		    	
		</item-list>         
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>