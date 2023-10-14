import React from "react";
import PropTypes from "prop-types";

const ResultDisplay = ({ loading, response, handleCopyToClipboard }) => {
    let content = null;

    if (loading) {
        content = (
            <div className="mt-4 flex items-center">
                <div className="animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-blue-500 mx-auto"></div>
                <span className="ml-2 text-gray-700">Trwa przetwarzanie pliku...</span>
            </div>
        );
    } else if (response) {
        content = (
            <div className="mt-4">
                <h2 className="text-2xl font-semibold mb-2">Wynik:</h2>
                <p className="text-gray-700 text-xl">{response.content}</p>
                <button
                    onClick={handleCopyToClipboard}
                    className="bg-blue-500 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md mt-2"
                >
                    Kopiuj do schowka
                </button>
            </div>
        );
    }

    return (
        <div className="mt-4">{content}</div>
    );
};

ResultDisplay.propTypes = {
    loading: PropTypes.bool.isRequired,
    response: PropTypes.object,
    handleCopyToClipboard: PropTypes.func.isRequired,
};

export default ResultDisplay;