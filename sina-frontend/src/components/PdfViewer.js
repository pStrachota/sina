import React from "react";
import { Viewer, Worker } from "@react-pdf-viewer/core";
import '@react-pdf-viewer/core/lib/styles/index.css';
import '@react-pdf-viewer/default-layout/lib/styles/index.css';


const PDFViewer = ({ viewPdf }) => {
    return (
        <div className="p-6 bg-white rounded-lg shadow-md w-1/2">
            <div className="mt-4">
                <h2 className="text-xl font-semibold mb-2">Podgląd PDF:</h2>
                {viewPdf ? (
                    <Worker workerUrl="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.4.120/pdf.worker.min.js">
                        <Viewer defaultScale={1} fileUrl={viewPdf} />
                    </Worker>
                ) : (
                    <p>Tu będzie dostępny podgląd wybranego przez Ciebie pliku PDF.</p>
                )}
            </div>
        </div>
    );
};

export default PDFViewer;
