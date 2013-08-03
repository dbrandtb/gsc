<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

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
			<item-list
				xsi:type="java:mx.com.aon.catbo.model.OperacionCATVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@CDDIALOGO">
					<xsl:element name="cd-dialogo">
						<xsl:value-of select="@CDDIALOGO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDSECUENCIA">
					<xsl:element name="cd-secuencia">
						<xsl:value-of select="@CDSECUENCIA" />
					</xsl:element>
				</xsl:if>
                <xsl:if test="@DSDIALOGO">
                    <xsl:element name="ds-dialogo">
                        <xsl:value-of select="@DSDIALOGO" />
                    </xsl:element>
                </xsl:if>
				<xsl:if test="@OTTAPVAL">
					<xsl:element name="ot-tap-val">
						<xsl:value-of select="@OTTAPVAL" />
					</xsl:element>
				</xsl:if>
				
				<xsl:if test="@DSLABEL">
					<xsl:element name="ds-label">
						<xsl:value-of select="@DSLABEL" />
					</xsl:element>
				</xsl:if>
				
                    
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>