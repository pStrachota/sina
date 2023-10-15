import React from "react";
import DocViewer, {DocViewerRenderers} from "@cyntler/react-doc-viewer";

const FileViewer = ({ viewPdf }) => {
    console.log(viewPdf)
    return (
        <div className="p-6 bg-white rounded-lg shadow-md w-1/2">
            <div className="mt-4">
                <h2 className="text-xl font-semibold mb-2">Podgląd wybranego pliku:</h2>
                {viewPdf ? (
                    <DocViewer  pluginRenderers={DocViewerRenderers} documents={[{ uri: window.URL.createObjectURL(viewPdf), fileName: viewPdf.name }]} />
                ) : (
                    <p>Tu będzie dostępny podgląd wybranego przez Ciebie pliku.</p>
                )}
            </div>
        </div>
    );
};

export default FileViewer;
