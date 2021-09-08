package com.adityaagusw.pdfa4

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adityaagusw.pdfa4.databinding.ActivityMainBinding
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.UnitValue
import java.io.ByteArrayOutputStream
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnPDF.setOnClickListener {
                createPDF()
            }
        }

    }

    private fun createPDF() {
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(pdfPath, "${System.currentTimeMillis()}.pdf")

        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)

        pdfDocument.defaultPageSize = PageSize.A4
        document.setMargins(18F, 18F, 18F, 18F)

        val d = getDrawable(R.drawable.bg_cover)
        val bitmap = (d as BitmapDrawable).bitmap

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val bitmapData = stream.toByteArray()

        val imageData = ImageDataFactory.create(bitmapData)
        val image = Image(imageData)
        document.add(image)

        val text = Paragraph("My Styled Text")
        document.add(text)

        val boldText = Paragraph("My Styled Text")
        boldText.setBold()
        document.add(boldText)

        val sizedText = Paragraph("My Sized Text")
        sizedText.setFontSize(20.0f)
        document.add(sizedText)

        val coloredText = Paragraph("My Sized Text")
        coloredText.setFontColor(ColorConstants.RED)
        document.add(coloredText)

        val alignedText = Paragraph("My Sized Text")
        alignedText.setTextAlignment(TextAlignment.CENTER)
        document.add(alignedText)

        //space
        val textWithoutSpace1 = Paragraph("My Text")
        textWithoutSpace1.setMargins(10f, 10f, 10f, 10f)
        document.add(textWithoutSpace1)

        val textWithSpace = Paragraph("My Spaced Text")
        textWithSpace.setMargins(10f, 10f, 10f, 10f)
        document.add(textWithSpace)

        val textWithoutSpace2 = Paragraph("My Text")
        textWithoutSpace2.setMargins(10f, 10f, 10f, 10f)
        document.add(textWithoutSpace2)

        // table
        val table = Table(
            UnitValue.createPercentArray(
                floatArrayOf(
                    8f,
                    23f,
                    15f,
                    15f,
                    12f,
                    12f,
                    15f
                )
            )
        ).useAllAvailableWidth()

        //Add Header Cells
        table.addHeaderCell(Cell().add(Paragraph("Date").setTextAlignment(TextAlignment.CENTER)))
        table.addHeaderCell(Cell().add(Paragraph("Job Name").setTextAlignment(TextAlignment.CENTER)))
        table.addHeaderCell(Cell().add(Paragraph("Job Size").setTextAlignment(TextAlignment.CENTER)))
        table.addHeaderCell(Cell().add(Paragraph("Job Type").setTextAlignment(TextAlignment.CENTER)))
        table.addHeaderCell(Cell().add(Paragraph("Quantity").setTextAlignment(TextAlignment.CENTER)))
        table.addHeaderCell(Cell().add(Paragraph("Rate").setTextAlignment(TextAlignment.CENTER)))
        table.addHeaderCell(Cell().add(Paragraph("Amount").setTextAlignment(TextAlignment.CENTER)))

        table.addCell(Cell().add(Paragraph("05 Maret 1998").setTextAlignment(TextAlignment.CENTER)))
        table.addCell("Programmer")
        table.addCell(Cell().add(Paragraph("5 Month").setTextAlignment(TextAlignment.CENTER)))
        table.addCell(
            Cell().add(
                Paragraph("Fixed".replace("Pouch", "")).setTextAlignment(
                    TextAlignment.CENTER
                )
            )
        )
        table.addCell(Cell().add(Paragraph("Yeah").setTextAlignment(TextAlignment.CENTER)))
        table.addCell(Cell().add(Paragraph("1722498511").setTextAlignment(TextAlignment.CENTER)))
        table.addCell(Cell().add(Paragraph("45jt").setTextAlignment(TextAlignment.RIGHT)))

        document.add(table)

        document.close()

        Toast.makeText(this, "PDF Dicetak", Toast.LENGTH_SHORT).show()
    }
}