import React from "react";
import PropTypes from "prop-types";

const FileUpload = ({
                        onFileChange,
                        handleUpload,
                        loading,
                        response,
                        selectedLanguage,
                        onLanguageChange,
                    }) => {
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
            </div>
        );
    }

    return (
        <div className="w-1/2 p-6 bg-white rounded-lg shadow-md mr-5">
            <div className="flex items-center justify-center w-full">
                <label
                    htmlFor="dropzone-file"
                    className="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-gray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600"
                >
                    <div className="flex flex-col items-center justify-center pt-5 pb-6">
                        <svg
                            className="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400"
                            aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 20 16"
                        >
                            <path
                                stroke="currentColor"
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                stroke-width="2"
                                d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2"
                            />
                        </svg>
                        <p className="mb-2 text-sm text-gray-500 dark:text-gray-400">
                            <span className="font-semibold">Click to upload</span> or drag and drop
                        </p>
                        <p className="text-xs text-gray-500 dark:text-gray-400">PDF (MAX 5 MB)</p>
                    </div>
                    <input
                        id="dropzone-file"
                        type="file"
                        accept=".pdf"
                        className="hidden"
                        onChange={onFileChange}
                    />
                </label>
            </div>
            <div className="flex flex-row justify-around mt-3" >
                <div className="flex flex-col " >
                    <h6>
                        Wybierz język streszczenia:
                    </h6>
                    <select className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" value={selectedLanguage} onChange={onLanguageChange}>
                        <option value="polish">Polski</option>
                        <option value="english">Angielski</option>
                        <option value="korean">Koreański</option>
                        <option value="german">Niemiecki</option>
                    </select>
                </div>
                <button
                    onClick={handleUpload}
                    className={`bg-purple-500 hover:bg-purple-700 text-white font-semibold py-2 px-4 rounded-md mt-2 ${
                        loading ? "opacity-50 cursor-not-allowed" : ""
                    }`}
                    disabled={loading}
                >
                    Prześlij PDF
                </button>
            </div>
            {content}
        </div>
    );
};

FileUpload.propTypes = {
    onFileChange: PropTypes.func.isRequired,
    handleUpload: PropTypes.func.isRequired,
    loading: PropTypes.bool.isRequired,
    response: PropTypes.object,
    selectedLanguage: PropTypes.string.isRequired,
    onLanguageChange: PropTypes.func.isRequired,
    error: PropTypes.string,
};

export default FileUpload;
