import React, { useCallback, useState } from "react";
import axios from "axios";
import PDFViewer from "./components/PdfViewer";
import FileUpload from "./components/FileUpload";

function App() {
  const [file, setFile] = useState(null);
  const [response, setResponse] = useState("");
  const [loading, setLoading] = useState(false);
  const [viewPdf, setViewPdf] = useState(null);
  const [error, setError] = useState(null);
  const [selectedLanguage, setSelectedLanguage] = useState("polish");

  const onFileChange = useCallback((event) => {
    setError(null)
    setResponse("")
    const selectedFile = event.target.files[0];
    setFile(selectedFile);
    if (selectedFile.size <= 5 * 1024 * 1024 && selectedFile.type === 'application/pdf') {
      let reader = new FileReader();
      reader.readAsDataURL(selectedFile);
      reader.onloadend = () => {
        setViewPdf(reader.result);
      };
    } else {
      setFile(null);
      alert("Plik musi być mniejszy niż 5MB i mieć rozszerzenie .pdf");
    }
  }, []);

  const handleUpload = async () => {
    if (file) {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("language", selectedLanguage);

      try {
        setLoading(true);
        const response = await axios.post(
            "http://localhost:8080/api/v1/extractor",
            formData,
            {
              headers: {
                "Content-Type": "multipart/form-data",
              },
            }
        );
        setResponse(response.data);
        setLoading(false);
      } catch (error) {
        if (error.response.data.exceptionMessage.includes("context_length_exceeded")) {
          setError("Zbyt długi tekst. Spróbuj ponownie z innym plikiem PDF.")
        } else if (error.response.data.exceptionMessage.includes("no_text_found")) {
          setError("Nie znaleziono tekstu w pliku PDF. Spróbuj ponownie z innym plikiem PDF.")
        } else if (error.response.data.exceptionMessage.includes("Cannot read text from PDF file")) {
          setError("Nie można odczytać tekstu z pliku PDF. Spróbuj ponownie z innym plikiem PDF.")
        }
        setResponse("")
        setLoading(false);
      }
    }
  };

  const handleLanguageChange = (event) => {
    setSelectedLanguage(event.target.value);
  };

  return (
      <>
        <div className="bg-white p-4 text-black shadow-xl flex items-center">
          <img className="mr-2 max-h-14 w-70" src="/logo2.jpg" />
          <p className="text-purple-500 text-2xl" >PDF summarizer</p>
        </div>

        <div className="mx-8  my-8 flex flex-row">
          <FileUpload onFileChange={onFileChange} handleUpload={handleUpload} loading={loading} response={response} error={error}
                      selectedLanguage={selectedLanguage}
                      onLanguageChange={handleLanguageChange}
          />
          <PDFViewer viewPdf={viewPdf} />
        </div>
      </>
  );
}

export default App;
