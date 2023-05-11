<?php

namespace App\Controller;

use App\Entity\Constat;
use App\Form\ConstatType;
use App\Repository\ConstatRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Dompdf\Dompdf;
use Dompdf\Options;
use Geocoder\Query\GeocodeQuery;
use Geocoder\Provider\GoogleMaps\GoogleMaps;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\Material\BarChart;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;
#[Route('/constat')]
class ConstatController extends AbstractController
{
    #[Route('/', name: 'app_constat_index', methods: ['GET'])]
    public function index(ConstatRepository $constatRepository): Response
    {
        return $this->render('constat/index.html.twig', [
            'constats' => $constatRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_constat_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ConstatRepository $constatRepository,SluggerInterface $slugger): Response
    {
        $constat = new Constat();
        $form = $this->createForm(ConstatType::class, $constat);
        $form->handleRequest($request);
        $mail = new PHPMailer(true);

        if ($form->isSubmitted() && $form->isValid()) {

$filePaths = [];

          $imageFile = $form->get('image_degats')->getData();
            if ($imageFile) {
                $originalfilename = pathinfo($constat->getNom(), flags: PATHINFO_FILENAME);
                $safefilename = $slugger->slug($originalfilename);
                $newFilename = $safefilename.'-'.uniqid() . '.' . $imageFile->guessExtension();

                try {
                    $imageFile->move(
                        $this->getParameter('constat_directory'),
                        $newFilename
                    
                    );
                } catch (FileException $e) {
                    // gestion de l'erreur
                }

                $constat->setImageDegats($newFilename);
                 
                $filePaths[] = $newFilename;
            }
            try {
            
                $time = date('H:i:s \O\n d/m/Y');
               
                // $nom = $form->get('nom')->getData();
                $id_client = $form->get('nom')->getData();
    
               
                $mail->SMTPDebug = SMTP::DEBUG_SERVER;
                $mail->isSMTP();
                $mail->Host       = 'smtp.gmail.com';
                $mail->SMTPAuth   = true;
                $mail->Username   = 'acyl.lazrag@esprit.tn';            
                $mail->Password   = '212JFT7540a';                               
                $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
                $mail->Port       = 587;
                $mail->addAddress('acyl.lazrag@esprit.tn' );     
                $mail->isHTML(true);                                  
                $mail->Subject = " Assurensea  ";
                $mail->Body    = " un neauveau constats a été ajouter";
    
                $mail->send();
                $this->addFlash('message','the email has been sent');
                // $flashy->success('Article Ajoutée','http://your-awesome-link.com');
    
            } catch (Exception $e) {
                echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
            }
    






            $constatRepository->save($constat, true);

            return $this->redirectToRoute('app_constat_new', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('constat/new.html.twig', [
            'constat' => $constat,

            'form' => $form,
        ]);

 
    }
    #[Route("addConstatJSON/new", name: "addconstatJSON")]
    public function addconstatJSON(Request $req,NormalizerInterface $Normalizer)
    {
        
        $em = $this->getDoctrine()->getManager();
        $constat = new constat();
        $constat->setIdClient($req->get('id_client'));
        $constat->setIdvehicule($req->get('id_vehicule'));
        $constat->setLieu($req->get('lieu'));

       
        $em->persist($constat);
        $em->flush();

        $jsonContent = $Normalizer->normalize($constat, 'json', ['groups' => 'reclamation']);
        return new Response(json_encode($jsonContent));
    }

    #[Route("/AllConstats", name: "list")]
    public function getConstats(ConstatRepository $repo, SerializerInterface $serializer)
    {
        $constats = $repo->findAll();
        $json = $serializer->serialize($constats, 'json', ['groups' => "constats"]);
        return new Response($json);
    }



    #[Route('/{id}', name: 'app_constat_show', methods: ['GET'])]
    public function show(Constat $constat): Response
    {
        return $this->render('constat/show.html.twig', [
            'constat' => $constat,
        ]);
    }
    #[Route('/constatp/{id}', name: 'app_constat_consatp', methods: ['GET'])]
    public function constatp(Constat $constat): Response
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        
        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        
        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('constat/constatp.html.twig', [
            'constat' => $constat,
        ]);
        
        // Load HTML to Dompdf
        $dompdf->loadHtml($html);
        
        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (inline view)
        $pdfOutput = $dompdf->output();
        return new Response($pdfOutput, 200, [
            'Content-Type' => 'application/pdf',
            'Content-Disposition' => 'inline; filename="mypdf.pdf"'

        ]);
    }

    #[Route('/{id}/edit', name: 'app_constat_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Constat $constat, ConstatRepository $constatRepository): Response
    {
        $form = $this->createForm(ConstatType::class, $constat);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $constatRepository->save($constat, true);

            return $this->redirectToRoute('app_constat_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('constat/edit.html.twig', [
            'constat' => $constat,
            'form' => $form,
        ]);
    }
    

    

    #[Route('/{id}', name: 'app_constat_delete', methods: ['POST'])]
    public function delete(Request $request, Constat $constat, ConstatRepository $constatRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$constat->getId(), $request->request->get('_token'))) {
            $constatRepository->remove($constat, true);
        }

        return $this->redirectToRoute('app_constat_index', [], Response::HTTP_SEE_OTHER);
    }
    
 
// // #[Route('/rech', name: 'rech', methods: ['GET'])]
// // public function fonc(Request $request , ConstatRepository $c): Response
// // {
// //     $qb = $c->createQueryBuilder('c')
// //     ->orderBy('c.id_client', 'ASC'); // default order
// //     // Add sorting based on the query parameters
// //     $sortField = $request->query->get('sortField', 'id_client');
// //     $sortDirection = $request->query->get('sortDirection', 'asc');
// //     $qb->orderBy("c.$sortField", $sortDirection);
// //     $q = $request->query->get('q');
// //         if ($q) {
// //             $qb->andWhere('c.lieu LIKE :search')
// //             ->setParameter('search', '%' . $q . '%');
// //         }
// //         return $this->render('constat/index.html.twig');
// }

  
}
